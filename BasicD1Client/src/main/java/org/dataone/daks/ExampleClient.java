/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dataone.daks;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.math.BigInteger;

import org.dataone.client.D1Client;
import org.dataone.client.MNode;
import org.dataone.client.auth.ClientIdentityManager;
import org.dataone.service.exceptions.BaseException;
import org.dataone.service.types.v1.AccessPolicy;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Checksum;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.SystemMetadata;
import org.dataone.service.util.Constants;

/**
 * ExampleClient is a command-line class that can be run to illustrate usage patterns
 * of the d1_libclient_java library methods and services.  Examples do not illustrate
 * all possible services that are available in the DataONE API, but rather are
 * meant to illustrate usage patterns.
 */
public class ExampleClient {

    /**
     * Execute the examples.
     */
    public static void main(String[] args) {
        
        //String currentUrl = "https://demo1.test.dataone.org:443/knb/d1/mn";
        //String currentUrl = "https://mn-demo-5.test.dataone.org/knb/d1/mn";
        String currentUrl = "https://mn-demo-9.test.dataone.org/knb/d1/mn";
        MNode mn = D1Client.getMN(currentUrl);

        runExampleCreate(mn);
    }

    /**
     * Demonstrate the execution of the MNRead.create() service on the given
     * Member Node.  This method creates a data object on the node with an
     * identifier based on the current date in milliseconds, so it probably 
     * should only be run against test servers, not production servers, to
     * avoid polluting production servers with test data.
     * @param mn the MNode member node on which to create the object
     */
    private static void runExampleCreate(MNode mn) {
        try {
            Identifier newid = new Identifier();
            String idstr = new String("test:" + System.currentTimeMillis());
            newid.setValue(idstr);
            String csv = "1,2,3";
            InputStream is = new StringBufferInputStream(csv);
            SystemMetadata sm = generateSystemMetadata(newid);
            Identifier pid = mn.create(null, newid, is, sm);
            System.out.println("Create completed with PID: " + pid.getValue());

        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a SystemMetadata object for the given Identifier, using fake values
     * for the SystemMetadata fields.
     * @param newid the Identifier of the object to be described
     * @return the SystemMetadata object that is created
     */
    private static SystemMetadata generateSystemMetadata(Identifier newid) {
        SystemMetadata sm = new SystemMetadata();
        sm.setIdentifier(newid);
        ObjectFormatIdentifier fmtid = new ObjectFormatIdentifier();
        fmtid.setValue("text/csv");
        sm.setFormatId(fmtid);
        sm.setSize(new BigInteger("5"));
        Checksum cs = new Checksum();
        cs.setAlgorithm("SHA-1");
        cs.setValue("879384579485739487534987");
        sm.setChecksum(cs);
        Subject clientSubject = ClientIdentityManager.getCurrentIdentity();
        sm.setRightsHolder(clientSubject);            
        sm.setSubmitter(clientSubject);
        AccessPolicy ap = new AccessPolicy();
        AccessRule ar = new AccessRule();
        Subject s = new Subject();
        s.setValue(Constants.SUBJECT_PUBLIC);
        ar.addSubject(s);
        ar.addPermission(Permission.READ);
        ap.addAllow(ar);
        sm.setAccessPolicy(ap);
        return sm;
    }
}


