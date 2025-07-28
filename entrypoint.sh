#!/bin/sh
# Obtiene la IP desde /etc/hosts, compatible con Alpine
IP=$(awk 'NR==2 {print $1}' /etc/hosts)
export JAVA_RMI_SERVER_HOSTNAME=$IP

echo "🟢 IP para RMI: $JAVA_RMI_SERVER_HOSTNAME"

java -cp "out:lib/*" edu.distribuido.rmi.server.RmiBootstrap
