#!/bin/bash

cd src/portfolio
ant war

cd ../../

cp ./src/portfolio/build/deploy/ROOT.war dist/

