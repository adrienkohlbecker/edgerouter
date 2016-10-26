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
            protocol icmp
        }
        rule 200 {
            action accept
            description "Allow HTTP/HTTPS"
            destination {
                port 80,443
            }
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
        description ""
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
        rule 30 {
            action accept
            description "Allow HTTP"
            destination {
                address 10.123.40.11
                port 80,443
            }
            log disable
            protocol tcp
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
        rule 41 {
            action accept
            description "Allow Gitlab SSH"
            destination {
                address 10.123.40.11
                port 2222
            }
            log disable
            protocol tcp
        }
        rule 42 {
            action accept
            description "Allow SFTP"
            destination {
                address 10.123.40.11
                port 2223
            }
            log disable
            protocol tcp
        }
        rule 43 {
            action accept
            description "Allow OpenVPN"
            destination {
                address 10.123.40.11
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
        description LAN
        duplex auto
        speed auto
        vif 10 {
            address 10.123.10.1/24
            description LAN_10_MGMT
            mtu 1500
        }
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
        description WAN
        duplex auto
        speed auto
        vif 200 {
            address dhcp
            description Internet
            dhcp-options {
                client-option "send vendor-class-identifier &quot;byteliad_data&quot;;"
                default-route update
                default-route-distance 210
                name-server update
            }
        }
    }
    ethernet eth2 {
        description LAN2
        disable
        duplex auto
        speed auto
    }
    loopback lo {
    }
}
port-forward {
    auto-firewall disable
    hairpin-nat enable
    lan-interface eth0.10
    lan-interface eth0.20
    lan-interface eth0.30
    lan-interface eth0.40
    rule 1 {
        description HTTPS
        forward-to {
            address 10.123.40.11
            port 443
        }
        original-port 443
        protocol tcp
    }
    rule 2 {
        description HTTP
        forward-to {
            address 10.123.40.11
            port 80
        }
        original-port 80
        protocol tcp
    }
    rule 3 {
        description Plex
        forward-to {
            address 10.123.40.11
            port 32400
        }
        original-port 32400
        protocol tcp
    }
    rule 4 {
        description OpenVPN
        forward-to {
            address 10.123.40.11
            port 1194
        }
        original-port 1194
        protocol udp
    }
    rule 5 {
        description "Gitlab SSH"
        forward-to {
            address 10.123.40.11
            port 2222
        }
        original-port 2222
        protocol tcp
    }
    rule 6 {
        description SFTP
        forward-to {
            address 10.123.40.11
            port 2223
        }
        original-port 2223
        protocol tcp
    }
    wan-interface eth1.200
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN_10_MGMT {
            authoritative disable
            subnet 10.123.10.0/24 {
                default-router 10.123.10.1
                dns-server 8.8.8.8
                dns-server 8.8.4.4
                dns-server 208.67.222.222
                dns-server 208.67.220.220
                lease 86400
                start 10.123.10.100 {
                    stop 10.123.10.200
                }
                static-mapping hypervisor-ipmi {
                    ip-address 10.123.10.10
                    mac-address 00:25:90:86:5a:ae
                }
                static-mapping meraki {
                    ip-address 10.123.10.3
                    mac-address 00:18:0a:7b:2b:7e
                }
                static-mapping switch {
                    ip-address 10.123.10.2
                    mac-address 38:63:bb:ed:bd:80
                }
            }
        }
        shared-network-name LAN_20_PRIVATE {
            authoritative disable
            subnet 10.123.20.0/24 {
                default-router 10.123.20.1
                dns-server 8.8.8.8
                dns-server 8.8.4.4
                dns-server 208.67.222.222
                dns-server 208.67.220.220
                lease 86400
                start 10.123.20.100 {
                    stop 10.123.20.200
                }
                static-mapping lg-g5 {
                    ip-address 10.123.20.31
                    mac-address 5c:70:a3:60:51:38
                }
                static-mapping mbp-ethernet {
                    ip-address 10.123.20.22
                    mac-address 00:e0:1b:6f:f8:44
                }
                static-mapping mbp-wifi {
                    ip-address 10.123.20.21
                    mac-address a4:5e:60:ca:20:df
                }
                static-mapping sonos-bridge {
                    ip-address 10.123.20.41
                    mac-address 00:0e:58:19:4e:be
                }
                static-mapping sonos-play1 {
                    ip-address 10.123.20.43
                    mac-address 00:0e:58:c9:41:dc
                }
                static-mapping sonos-play5 {
                    ip-address 10.123.20.42
                    mac-address 00:0e:58:8e:8e:50
                }
            }
        }
        shared-network-name LAN_30_GUEST {
            authoritative disable
            subnet 10.123.30.0/24 {
                default-router 10.123.30.1
                dns-server 8.8.8.8
                dns-server 8.8.4.4
                dns-server 208.67.222.222
                dns-server 208.67.220.220
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
            authoritative disable
            subnet 10.123.40.0/24 {
                default-router 10.123.40.1
                dns-server 8.8.8.8
                dns-server 8.8.4.4
                dns-server 208.67.222.222
                dns-server 208.67.220.220
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
            listen-on eth0.10
            listen-on eth0.20
            listen-on eth0.30
            listen-on eth0.40
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
            outbound-interface eth1.200
            type masquerade
        }
    }
    ssh {
        port 22
        protocol-version v2
    }
    upnp2 {
        listen-on eth0
        listen-on eth0.10
        listen-on eth0.20
        listen-on eth0.30
        listen-on eth0.40
        listen-on eth2
        nat-pmp enable
        secure-mode disable
        wan eth1.200
    }
}
system {
    config-management {
        commit-revisions 50
    }
    host-name ubnt
    login {
        user ubnt {
            authentication {
                encrypted-password $1$zKNoUbAo$gomzUbYvgyUMcD436Wo66.
            }
            level admin
        }
    }
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
        repository wheezy-security {
            components main
            distribution wheezy/updates
            password ""
            url http://security.debian.org
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
    time-zone Europe/Paris
    traffic-analysis {
        dpi enable
        export enable
    }
}
zone-policy {
    zone LAN_10_MGMT {
        default-action drop
        from LAN_20_PRIVATE {
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
        interface eth0.10
    }
    zone LAN_20_PRIVATE {
        default-action drop
        from LAN_10_MGMT {
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
                name ACCEPT_PING
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
        from LOCAL {
            firewall {
                name ACCEPT_PING
            }
        }
        from WAN {
            firewall {
                name WAN_TO_DMZ
            }
        }
        interface eth0.40
    }
    zone LOCAL {
        default-action drop
        from LAN_10_MGMT {
            firewall {
                name ACCEPT_NETWORKING
            }
        }
        from LAN_20_PRIVATE {
            firewall {
                name ACCEPT_NETWORKING_AND_MGMT
            }
        }
        from LAN_30_GUEST {
            firewall {
                name ACCEPT_NETWORKING
            }
        }
        from LAN_40_DMZ {
            firewall {
                name ACCEPT_NETWORKING
            }
        }
        from WAN {
            firewall {
                name DROP_EXCEPT_ESTABLISHED
            }
        }
        local-zone
    }
    zone WAN {
        default-action drop
        from LAN_10_MGMT {
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
        from LOCAL {
            firewall {
                name ACCEPT_ALL
            }
        }
        interface eth1.200
    }
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@5:nat@3:qos@1:quagga@2:system@4:ubnt-pptp@1:ubnt-util@1:vrrp@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v1.9.0.4901118.160804.1131 */
