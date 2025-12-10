<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <html>
            <body>
                <h3>XY Órarend – 2025. I. félév.</h3>

                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>ID</th>
                        <th>Típus</th>
                        <th>Tárgy</th>
                        <th>Időpont</th>
                        <th>Helyszín</th>
                        <th>Oktató</th>
                        <th>Szak</th>
                    </tr>

                    <xsl:for-each select="D1H8VP_orarend/ora">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="@tipus"/></td>

                            <td><xsl:value-of select="targy"/></td>

                            <td>
                                <xsl:value-of select="idopont/nap"/>
                                <xsl:text> </xsl:text>
                                <xsl:value-of select="idopont/tol"/>
                                <xsl:text>-</xsl:text>
                                <xsl:value-of select="idopont/ig"/>
                            </td>

                            <td><xsl:value-of select="helyszin"/></td>
                            <td><xsl:value-of select="oktato"/></td>
                            <td><xsl:value-of select="szak"/></td>
                        </tr>
                    </xsl:for-each>

                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>