#!/bin/sh
# wait-for-it.sh
# Czeka, aż KONKRETNY REALM w Keycloak będzie gotowy, a następnie uruchamia przekazaną komendę.

set -e

host="$1"
shift
cmd="$@"

# Pętla, która czeka na endpoint konfiguracji OpenID dla naszego realmu.
# To jest DOKŁADNIE to, czego potrzebuje Spring Security.
until wget -q -O /dev/null --tries=1 --timeout=2 "http://$host/realms/fixpoint-realm/.well-known/openid-configuration"; do
  >&2 echo "Realm Keycloaka jest niedostępny - czekam..."
  sleep 2
done

>&2 echo "Realm Keycloaka jest gotowy - uruchamiam aplikację..."
exec $cmd