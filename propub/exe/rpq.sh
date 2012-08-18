#!/bin/bash

cd /Users/sean/ProPubProj/propub/exe

dlv -silent $1 rpq_opmtosdm.dlv $2 rpq.dlv > rpq_out.dlv

exit
