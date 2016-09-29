firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name WAN_IN {
        default-action drop
        description "packets from Internet to LAN"
        enable-default-log
        rule 1 {
            action accept
            description "allow established sessions"
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
            description "drop invalid state"
            log disable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
    }
    name WAN_LOCAL {
        default-action drop
        description "packets from Internet to the router"
        rule 1 {
            action accept
            description "allow established session to the router"
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
            description "drop invalid state"
            log disable
            protocol all
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
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
        description LAN1
        duplex auto
        speed auto
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
            firewall {
                in {
                    name WAN_IN
                }
                local {
                    name WAN_LOCAL
                }
            }
        }
    }
    ethernet eth2 {
        address 10.123.1.1/24
        description LAN2
        duplex auto
        speed auto
    }
    loopback lo {
    }
}
port-forward {
    auto-firewall enable
    hairpin-nat enable
    lan-interface eth0
    lan-interface eth2
    rule 1 {
        description HTTPS
        forward-to {
            address 10.123.0.11
            port 443
        }
        original-port 443
        protocol tcp
    }
    rule 2 {
        description HTTP
        forward-to {
            address 10.123.0.11
            port 80
        }
        original-port 80
        protocol tcp
    }
    rule 3 {
        description Plex
        forward-to {
            address 10.123.0.11
            port 32400
        }
        original-port 32400
        protocol tcp
    }
    rule 4 {
        description OpenVPN
        forward-to {
            address 10.123.0.11
            port 1194
        }
        original-port 1194
        protocol udp
    }
    wan-interface eth1.200
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN1 {
            authoritative disable
            subnet 10.123.0.0/24 {
                default-router 10.123.0.1
                dns-server 10.123.0.1
                lease 86400
                start 10.123.0.100 {
                    stop 10.123.0.200
                }
                static-mapping hypervisor-eth0 {
                    ip-address 10.123.0.11
                    mac-address 00:25:90:86:77:88
                }
                static-mapping hypervisor-eth1 {
                    ip-address 10.123.0.12
                    mac-address 00:25:90:86:77:89
                }
                static-mapping hypervisor-ipmi {
                    ip-address 10.123.0.10
                    mac-address 00:25:90:86:5a:ae
                }
                static-mapping switch {
                    ip-address 10.123.0.2
                    mac-address 38:63:bb:ed:bd:80
                }
                static-mapping meraki {
                    ip-address 10.123.0.3
                    mac-address 00:18:0a:7b:2b:7e
                }
                static-mapping samsung-M2675FN {
                    ip-address 10.123.0.4
                    mac-address 30:cd:a7:b8:15:be
                }
            }
        }
    }
    dns {
        forwarding {
            cache-size 1000
            listen-on eth0
            listen-on eth2
        }
    }
    gui {
        https-port 443
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
        ipv4 {
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


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@4:nat@3:qos@1:quagga@2:system@4:ubnt-pptp@1:ubnt-util@1:vrrp@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v1.7.0.4783374.150622.1534 */
