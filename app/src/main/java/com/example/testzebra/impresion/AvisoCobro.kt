package com.example.testzebra.impresion

class AvisoCobro {
    companion object {
        fun getData(data: HashMap<String, String>): String {
            var source: String = """
                ﻿CT~~CD,~CC^~CT~
                ^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ
                ^XA
                ^MMT
                ^PW799
                ^LL0951
                ^LS0
                ^FO32,32^GFA,01536,01536,00012,:Z64:
                eJzNkzFLw1AQxy+vrXlEsB0cHMSEdClZFFwCDmkH6ScouPlJSh+4FAf7EQxOpYurk/ZLuAdHFx0Fbc/XmHd3xXZy8aZfflzy/rlcAP5aNcPsbfGNObNfCBa+KXwoOHvfzMcfzP0v5smS44Ts1S17CJlVhuxHzBrR0AUiBW0jUjhP8JVgH5GCThBdUK/JXoXs65Zd0CBDpHAjZhuHuGXZBT1dsaE45NvCn6+4eoG3FVdB+8Kj8IjPg0HX8V0EkXs8DcR6E0D1fFyQVjZkHEAUV/20ELg0euz6xdeyQ4hyiMsTRnY4LecL6GgTlX44B12vfJZDcgRJySHAwT78BO0YSJULtF55rIgj4VPR3YDqAAWqqQKnvZ1Wi3omn1PiIS/i7usL8b5HPsjvC9IN2h+VPK3tifNr+9MTvhC+m7Gfhzgm7y/oXUxb7K3HbDSx/SiXTqtypJX3wCc/y+UPLCpKI744PGFOU+EvCPWN8HUeJ+w9CN9lPis4f29OXLvmPD53w2Mg8ogyGy0oVS7nr9J6Ju+gPHEcJHnFIrzdOL3lgOlm/z/rGx7zvOk=:F9B3
                ^FT265,78^A0N,45,45^FH\^FDSeLA-ORURO^FS
                ^FT300,110^A0N,28,28^FH\^FDAVISO DE COBRO^FS
                ^FT265,141^A0N,25,24^FH\^FDPERIODO^FS
                ^FT421,137^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo^FS^CI0
                ^FT56,194^A0N,25,24^FH\^FDCUENTA^FS
                ^FT230,194^A0N,25,24^FH\^FDCATASTRO^FS
                ^FT429,194^A0N,25,24^FH\^FDMEDIDOR^FS
                ^FT622,194^A0N,25,24^FH\^FDFECHA^FS
                ^FT56,219^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@cuenta^FS^CI0
                ^FT230,219^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@catastro^FS^CI0
                ^FT429,219^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@medidor^FS^CI0
                ^FT622,219^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@medidor^FS^CI0
                ^FT56,263^A0N,25,24^FH\^FDCATEGORIA^FS
                ^FT56,329^A0N,25,24^FH\^FDNOMBRE^FS
                ^FT56,397^A0N,25,24^FH\^FDDIRECCION^FS
                ^FT188,460^A0N,25,24^FH\^FDFECHA^FS
                ^FT339,460^A0N,25,24^FH\^FDLECTURA^FS
                ^FT522,460^A0N,25,24^FH\^FDHISTORIAL^FS
                ^FT56,499^A0N,25,24^FH\^FDANTERIOR^FS
                ^FT56,527^A0N,25,24^FH\^FDACTUAL^FS
                ^FT56,557^A0N,25,24^FH\^FDCONSUMO ACTUAL^FS
                ^FT56,290^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@categoria^FS^CI0
                ^FT56,357^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@nombre^FS^CI0
                ^FT56,425^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@direccion^FS^CI0
                ^FO501,435^GB0,208,4^FS
                ^FT622,141^A0N,25,24^FH\^FDNro.^FS
                ^FT683,135^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@nro^FS^CI0
                ^FT188,491^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@fecha_anterior^FS^CI0
                ^FT188,519^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@fecha_actual^FS^CI0
                ^FT339,491^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@lectura_anterior^FS^CI0
                ^FT339,519^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@lectura_actual^FS^CI0
                ^FT339,549^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo_actual^FS^CI0
                ^FT57,780^A@N,17,18,TT0003M_^FH\^CI17^F8^FDSI SU DEUDA ES MAYOR A 2 MESES SE COBRARÁ LA MULTA POR MORA EN CAJA^FS^CI0
                ^FT56,810^A0N,20,19^FH\^FDSi Ud. cancel\A2 en caja meses pendientes, no tome en cuenta el valor de la factura.^FS
                ^FT56,834^A0N,20,19^FH\^FDEstimado cliente, en caso de encontrar diferencias en la lectura actual y la lectura de su ^FS
                ^FT56,858^A0N,20,19^FH\^FDmedidor, favor presentar el reclamo en ODECO para la correci\A2n en el mes. \ADGracias!^FS
                ^FT522,491^A0N,25,24^FH\^FDPERIODO^FS
                ^FT522,517^A@N,17,18,TT0003M_^FH\^CI17^F8^FD1^FS^CI0
                ^FT522,538^A@N,17,18,TT0003M_^FH\^CI17^F8^FD2^FS^CI0
                ^FT522,561^A@N,17,18,TT0003M_^FH\^CI17^F8^FD3^FS^CI0
                ^FT522,581^A@N,17,18,TT0003M_^FH\^CI17^F8^FD4^FS^CI0
                ^FT522,601^A@N,17,18,TT0003M_^FH\^CI17^F8^FD5^FS^CI0
                ^FT522,623^A@N,17,18,TT0003M_^FH\^CI17^F8^FD6^FS^CI0
                ^FT538,517^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo1^FS^CI0
                ^FT658,491^A0N,25,24^FH\^FDCONSUMO^FS
                ^FT658,517^A@N,17,18,TT0003M_^FB101,1,0,R^FH\^CI17^F8^FD@consumo1^FS^CI0
                ^FT538,538^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo2^FS^CI0
                ^FT538,561^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo3^FS^CI0
                ^FT538,581^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo4^FS^CI0
                ^FT538,601^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo5^FS^CI0
                ^FT538,622^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo6^FS^CI0
                ^FT658,538^A@N,17,18,TT0003M_^FB101,1,0,R^FH\^CI17^F8^FD@consumo2^FS^CI0
                ^FT658,561^A@N,17,18,TT0003M_^FB101,1,0,R^FH\^CI17^F8^FD@consumo3^FS^CI0
                ^FT658,581^A@N,17,18,TT0003M_^FB101,1,0,R^FH\^CI17^F8^FD@consumo4^FS^CI0
                ^FT658,601^A@N,17,18,TT0003M_^FB101,1,0,R^FH\^CI17^F8^FD@consumo5^FS^CI0
                ^FT658,622^A@N,17,18,TT0003M_^FB101,1,0,R^FH\^CI17^F8^FD@consumo6^FS^CI0
                ^FT56,587^A@N,17,18,TT0003M_^FH\^CI17^F8^FDFACTURA MES @MAR. Bs.^FS^CI0
                ^FT56,611^A@N,17,18,TT0003M_^FH\^CI17^F8^FDFACTURA PENDIENTE  Bs.^FS^CI0
                ^FT206,635^A0N,17,16^FH\^FDTOTAL Bs.^FS
                ^FT339,585^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@factura_mes_bs^FS^CI0
                ^FT339,609^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@pendiente_bs^FS^CI0
                ^FT339,633^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@total_bs^FS^CI0
                ^FT56,692^A0N,25,24^FH\^FDOBSERVACION: ^FS
                ^FT354,687^A@N,17,18,TT0003M_^FH\^CI17^F8^FDDEUDA ANTERIOR^FS^CI0
                ^FT330,687^A@N,17,18,TT0003M_^FH\^CI17^F8^FD(1)^FS^CI0
                ^FT623,59^A0N,17,16^FH\^FDOP:^FS
                ^FT623,84^A0N,17,16^FH\^FDUS:^FS
                ^FT653,56^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@operador^FS^CI0
                ^FT653,80^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@usuario^FS^CI0
                ^FT56,731^A0N,25,24^FH\^FDFECHA DE FACTURACION:^FS
                ^FT330,727^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@fecha_facturacion^FS^CI0
                ^PQ1,0,1,Y^XZ
                """.trimIndent()

            //reemplazando los parametros
            for ((k, v) in data) {
                source = source.replace(k, v)
            }

            return source
        }
    }
}