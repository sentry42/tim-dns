# Version 0.0.2-SNAPSHOT

- Switched from single `TimDns` class to `TimDns` and `TimDnaApp` with builder pattern.
- Created extensible command-line arguments

# Version 0.0.1-SNAPSHOT

- It works.

```
GET https://tim-dns.herokuapp.com/dns/desktop

POST https://tim-dns.herokuapp.com/dns
{
    "hostname":"desktop",
    "ip":"192.168.0.109"
}
```