#!/bin/bash

cd /Users/sean/ProPubProj/propub/exe

dlv -silent propub_swallow.dlv $1 $2 > out.txt

exit
