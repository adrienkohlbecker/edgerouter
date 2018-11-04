firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name ACCEPT_ALL {
        default-action accept
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
    }
    name ACCEPT_NETWORKING {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid state"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 100 {
            action accept
            protocol icmp
        }
        rule 600 {
            action accept
            description "Allow DNS"
            destination {
                port 53
            }
            protocol tcp_udp
        }
        rule 700 {
            action accept
            description "Allow DHCP"
            destination {
                port 67,68
            }
            protocol udp
        }
    }
    name ACCEPT_NETWORKING_AND_MDNS {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid state"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 100 {
            action accept
            protocol icmp
        }
        rule 600 {
            action accept
            description "Allow DNS"
            destination {
                port 53
            }
            protocol tcp_udp
        }
        rule 700 {
            action accept
            description "Allow DHCP"
            destination {
                port 67,68
            }
            protocol udp
        }
        rule 701 {
            action accept
            description "Allow mDNS"
            destination {
                port 5353
            }
            log disable
            protocol udp
        }
    }
    name ACCEPT_NETWORKING_AND_MGMT {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid state"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 100 {
            action accept
            description "Allow ICMP"
            log disable
            protocol icmp
        }
        rule 200 {
            action accept
            description "Allow HTTP/HTTPS"
            destination {
                port 80,443,8443
            }
            log disable
            protocol tcp
        }
        rule 600 {
            action accept
            description "Allow DNS"
            destination {
                port 53
            }
            protocol tcp_udp
        }
        rule 700 {
            action accept
            description "Allow DHCP"
            destination {
                port 67,68
            }
            protocol udp
        }
        rule 800 {
            action accept
            description "Allow SSH"
            destination {
                port 22
            }
            protocol tcp
        }
        rule 801 {
            action accept
            description "Allow mDNS"
            destination {
                port 5353
            }
            log disable
            protocol udp
        }
    }
    name ACCEPT_PING {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 3 {
            action accept
            description "Accept ICMP"
            log disable
            protocol icmp
        }
    }
    name ACCEPT_PING_AND_MDNS {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 3 {
            action accept
            description "Accept ICMP"
            log disable
            protocol icmp
        }
        rule 4 {
            action accept
            description "Accept mDNS"
            destination {
                port 5353
            }
            log disable
            protocol udp
        }
    }
    name GUEST_TO_PRIVATE {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 4 {
            action accept
            description "Accept Plex"
            destination {
                port 32400
            }
            log disable
            protocol tcp
        }
        rule 5 {
            action accept
            description "Accept Arq SSH"
            destination {
                port 2224
            }
            log disable
            protocol tcp
        }
        rule 6 {
            action accept
            description "Allow wireguard"
            destination {
                address 10.123.40.11
                port 51820
            }
            log disable
            protocol udp
        }
    }
    name DROP_EXCEPT_ESTABLISHED {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid state"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
    }
    name WAN_TO_DMZ {
        default-action drop
        enable-default-log
        rule 10 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 40 {
            action accept
            description "Allow Plex"
            destination {
                address 10.123.40.11
                port 32400
            }
            log disable
            protocol tcp
        }
        rule 42 {
            action accept
            description "Allow brumath SSH"
            destination {
                address 10.123.40.11
                port 2223
            }
            log disable
            protocol tcp
        }
        rule 44 {
            action accept
            description "Allow arq SSH"
            destination {
                address 10.123.40.11
                port 2224
            }
            log disable
            protocol tcp
        }
        rule 45 {
            action accept
            description "Allow wireguard"
            destination {
                address 10.123.40.11
                port 51820
            }
            log disable
            protocol udp
        }
    }
    name WAN_TO_LOCAL {
        default-action drop
        enable-default-log
        rule 1 {
            action accept
            description "Allow established / related"
            log disable
            protocol all
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 2 {
            action drop
            description "Drop invalid state"
            log enable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
        rule 3 {
            action accept
            description "Allow OpenVPN"
            destination {
                port 1194
            }
            log disable
            protocol udp
        }
    }
    options {
        mss-clamp {
            interface-type pppoe
            interface-type pptp
            interface-type tun
            mss 1452
        }
    }
    receive-redirects disable
    send-redirects enable
    source-validation disable
    syn-cookies enable
}
interfaces {
    ethernet eth0 {
        address 10.123.0.1/24
        description LAN
        duplex auto
        speed auto
        vif 20 {
            address 10.123.20.1/24
            description LAN_20_PRIVATE
            mtu 1500
        }
        vif 30 {
            address 10.123.30.1/24
            description LAN_30_GUEST
            mtu 1500
        }
        vif 40 {
            address 10.123.40.1/24
            description LAN_40_DMZ
            mtu 1500
        }
    }
    ethernet eth1 {
        address dhcp
        description WAN
        dhcp-options {
            name-server no-update
        }
        duplex auto
        speed auto
    }
    ethernet eth2 {
        description LAN2
        disable
        duplex auto
        speed auto
    }
    loopback lo {
    }
    openvpn vtun0 {
        description VPN
        encryption aes256
        hash sha256
        local-port 1194
        mode server
        openvpn-option "--push redirect-gateway"
        openvpn-option "--push dhcp-option DNS 10.123.50.1"
        openvpn-option --comp-lzo
        openvpn-option --persist-key
        openvpn-option --persist-tun
        openvpn-option "--keepalive 10 120"
        openvpn-option "--user nobody"
        openvpn-option "--group nogroup"
        openvpn-option "--tls-auth /config/auth/vpn-tlsauth.pem 0"
        protocol udp
        server {
            subnet 10.123.50.0/24
            topology subnet
        }
        tls {
            ca-cert-file /config/auth/vpn-ca.pem
            cert-file /config/auth/vpn-server.pem
            dh-file /config/auth/vpn-dhparam.pem
            key-file /config/auth/vpn-server-key.pem
        }
    }
}
port-forward {
    auto-firewall disable
    hairpin-nat enable
    lan-interface eth0
    lan-interface eth0.20
    lan-interface eth0.30
    lan-interface eth0.40
    lan-interface vtun0
    rule 1 {
        description Plex
        forward-to {
            address 10.123.40.11
            port 32400
        }
        original-port 32400
        protocol tcp
    }
    rule 2 {
        description ssh-brumath
        forward-to {
            address 10.123.40.11
            port 2223
        }
        original-port 2223
        protocol tcp
    }
    rule 3 {
        description ssh-arq
        forward-to {
            address 10.123.40.11
            port 2224
        }
        original-port 2224
        protocol tcp
    }
    rule 4 {
        description wireguard
        forward-to {
            address 10.123.40.11
            port 51820
        }
        original-port 51820
        protocol udp
    }
    wan-interface eth1
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN {
            authoritative enable
            subnet 10.123.0.0/24 {
                default-router 10.123.0.1
                dns-server 10.123.0.1
                lease 86400
                start 10.123.0.100 {
                    stop 10.123.0.200
                }
                static-mapping hypervisor-ipmi {
                    ip-address 10.123.0.10
                    mac-address 00:25:90:86:5a:ae
                }
                static-mapping unifi {
                    ip-address 10.123.0.2
                    mac-address b8:27:eb:ff:5a:e9
                }
            }
        }
        shared-network-name LAN_20_PRIVATE {
            authoritative enable
            subnet 10.123.20.0/24 {
                default-router 10.123.20.1
                dns-server 10.123.20.1
                lease 86400
                start 10.123.20.100 {
                    stop 10.123.20.200
                }
            }
        }
        shared-network-name LAN_30_GUEST {
            authoritative enable
            subnet 10.123.30.0/24 {
                default-router 10.123.30.1
                dns-server 10.123.30.1
                lease 86400
                start 10.123.30.100 {
                    stop 10.123.30.200
                }
                static-mapping printer {
                    ip-address 10.123.30.4
                    mac-address 30:cd:a7:b8:15:be
                }
            }
        }
        shared-network-name LAN_40_DMZ {
            authoritative enable
            subnet 10.123.40.0/24 {
                default-router 10.123.40.1
                dns-server 10.123.40.1
                lease 86400
                start 10.123.40.100 {
                    stop 10.123.40.200
                }
                static-mapping hypervisor-eth0 {
                    ip-address 10.123.40.11
                    mac-address 00:25:90:86:77:88
                }
                static-mapping hypervisor-eth1 {
                    ip-address 10.123.40.12
                    mac-address 00:25:90:86:77:89
                }
            }
        }
        use-dnsmasq disable
    }
    dns {
        forwarding {
            cache-size 1000
            listen-on eth0
            listen-on eth0.20
            listen-on eth0.30
            listen-on eth0.40
            listen-on vtun0
            options rebind-domain-ok=/plex.direct/
            options address=/wireguard.kohlby.fr/10.123.40.11
            options address=/couchpotato.kohlby.fr/10.123.40.11
            options address=/gogs.kohlby.fr/10.123.40.11
            options address=/portainer.kohlby.fr/10.123.40.11
            options address=/sabnzbd.kohlby.fr/10.123.40.11
            options address=/sickrage.kohlby.fr/10.123.40.11
            options address=/speedtest.kohlby.fr/10.123.40.11
            options address=/traefik.kohlby.fr/10.123.40.11
            options strict-order
            system
        }
    }
    gui {
        http-port 80
        https-port 443
        older-ciphers disable
    }
    mdns {
        reflector
    }
    nat {
        rule 5010 {
            outbound-interface eth1
            type masquerade
        }
    }
    ssh {
        port 22
        protocol-version v2
    }
    ubnt-discover {
        disable
    }
    upnp2 {
        listen-on eth0
        listen-on eth0.20
        listen-on eth0.30
        listen-on eth0.40
        listen-on eth2
        nat-pmp enable
        secure-mode disable
        wan eth1
    }
}
system {
    config-management {
        commit-revisions 50
    }
    host-name ubnt
    login {
        user ak {
            authentication {
                encrypted-password $6$TE36sZQb0V5$P4by4Sna4lgrY2bMuhV3RkjCZP8DIRo3gQZqc3vRkwANGNJhIslD7xncbVWvM6CYUaLvEIUuCtP4Hs3x9oGb9/
                plaintext-password ""
            }
            full-name "Adrien Kohlbecker"
            level admin
        }
    }
    name-server 1.1.1.1
    name-server 1.0.0.1
    name-server 8.8.8.8
    name-server 8.8.4.4
    name-server 208.67.222.222
    name-server 208.67.220.220
    ntp {
        server 0.ubnt.pool.ntp.org {
        }
        server 1.ubnt.pool.ntp.org {
        }
        server 2.ubnt.pool.ntp.org {
        }
        server 3.ubnt.pool.ntp.org {
        }
    }
    offload {
        hwnat disable
        ipv4 {
            forwarding enable
            gre enable
            pppoe disable
            vlan enable
        }
        ipv6 {
            forwarding enable
            pppoe disable
            vlan enable
        }
    }
    package {
        repository wheezy {
            components "main contrib non-free"
            distribution wheezy
            password ""
            url http://http.us.debian.org/debian
            username ""
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level warning
            }
        }
    }
    time-zone UTC
    traffic-analysis {
        dpi enable
        export enable
    }
}
zone-policy {
    zone LAN_00_DEFAULT {
        default-action drop
        from LAN_20_PRIVATE {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_50_VPN {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LOCAL {
            firewall {
                name ACCEPT_PING
            }
        }
        from WAN {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        interface eth0
    }
    zone LAN_20_PRIVATE {
        default-action drop
        from LAN_00_DEFAULT {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LAN_30_GUEST {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LAN_40_DMZ {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LOCAL {
            firewall {
                name ACCEPT_PING_AND_MDNS
            }
        }
        from WAN {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        interface eth0.20
    }
    zone LAN_30_GUEST {
        default-action drop
        from LAN_20_PRIVATE {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_40_DMZ {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LAN_50_VPN {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LOCAL {
            firewall {
                name ACCEPT_PING
            }
        }
        from WAN {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        interface eth0.30
    }
    zone LAN_40_DMZ {
        default-action drop
        from LAN_20_PRIVATE {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_30_GUEST {
            firewall {
                name GUEST_TO_PRIVATE
            }
        }
        from LAN_50_VPN {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LOCAL {
            firewall {
                name ACCEPT_PING_AND_MDNS
            }
        }
        from WAN {
            firewall {
                name WAN_TO_DMZ
            }
        }
        interface eth0.40
    }
    zone LAN_50_VPN {
        default-action drop
        from LAN_00_DEFAULT {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LAN_30_GUEST {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LAN_40_DMZ {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        from LOCAL {
            firewall {
                name ACCEPT_PING_AND_MDNS
            }
        }
        from WAN {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        interface vtun0
    }
    zone LOCAL {
        default-action drop
        from LAN_00_DEFAULT {
            firewall {
                name ACCEPT_NETWORKING_AND_MGMT
            }
        }
        from LAN_20_PRIVATE {
            firewall {
                name ACCEPT_NETWORKING_AND_MGMT
            }
        }
        from LAN_30_GUEST {
            firewall {
                name ACCEPT_NETWORKING_AND_MDNS
            }
        }
        from LAN_40_DMZ {
            firewall {
                name ACCEPT_NETWORKING_AND_MDNS
            }
        }
        from LAN_50_VPN {
            firewall {
                name ACCEPT_NETWORKING_AND_MGMT
            }
        }
        from WAN {
            firewall {
                name WAN_TO_LOCAL
            }
        }
        local-zone
    }
    zone WAN {
        default-action drop
        from LAN_00_DEFAULT {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_20_PRIVATE {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_30_GUEST {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_40_DMZ {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LAN_50_VPN {
            firewall {
                name ACCEPT_ALL
            }
        }
        from LOCAL {
            firewall {
                name ACCEPT_ALL
            }
        }
        interface eth1
    }
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@5:nat@3:qos@1:quagga@2:system@4:ubnt-pptp@1:ubnt-util@1:vrrp@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v1.9.1.4939093.161214.0705 */
