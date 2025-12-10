<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <rendezett_autok>
            <xsl:for-each select="autok/auto">
                <xsl:sort select="ar" data-type="number"/>
                <auto_adat>
                    <rendszam><xsl:value-of select="@rsz"/></rendszam>
                    <ar><xsl:value-of select="ar"/></ar>
                </auto_adat>
            </xsl:for-each>
        </rendezett_autok>
    </xsl:template>
</xsl:stylesheet>