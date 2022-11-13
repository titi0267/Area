#!/bin/bash

status=$(nc -z db 3306; echo $?)

while [ $status != 0 ]
do
  sleep 3s
  status=$(nc -z db 3306; echo $?)
done