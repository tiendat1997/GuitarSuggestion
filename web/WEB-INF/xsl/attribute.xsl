<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template name="attribute">
        <xsl:param name="name"/>
        <xsl:param name="detail"/>       
        <xsl:element name="attribute" xmlns="http://tiendat.io/schema/attribute">            
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>
            <xsl:element name="content">
                <xsl:value-of select="$detail"/>
            </xsl:element>            
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
