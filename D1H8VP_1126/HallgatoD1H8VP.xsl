<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Hallgatók Listája</h2>

                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Vezetéknév</th>
                        <th>Keresztnév</th>
                        <th>Foglalkozás</th>
                        <th>Ösztöndíj</th>
                    </tr>

                    <xsl:for-each select="hallgatok/hallgato">
                        <tr>
                            <td><xsl:value-of select="vezeteknev"/></td>
                            <td><xsl:value-of select="keresztnev"/></td>
                            <td><xsl:value-of select="foglalkozas"/></td>
                            <td><xsl:value-of select="ösztöndij"/></td>
                        </tr>
                    </xsl:for-each>

                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>