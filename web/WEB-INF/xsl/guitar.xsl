<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : guitar.xsl
    Created on : March 3, 2019, 1:26 PM
    Author     : DATTTSE62330
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">    
    
    <xsl:template name="guitarTemplate">        
        <xsl:param name="name"/>
        <xsl:param name="category"/>
        <xsl:param name="price"/>
        <xsl:param name="imageUrl"/>
        <xsl:param name="listFeature"/>
        <xsl:param name="attrNameXpath"/>
        <xsl:param name="attrDetailXpath"/>        
        <xsl:element name="guitar" xmlns="http://tiendat.io/schema/guitar">            
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>         
            <xsl:element name="category">
                <xsl:value-of select="$category"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:value-of select="$price"/>
            </xsl:element>
            <xsl:element name="imageUrl">
                <xsl:value-of select="$imageUrl"/>
            </xsl:element>
            <xsl:element name="attributes">  
                <xsl:for-each select="$listFeature">                    
                    <xsl:call-template name="attribute">
                        <xsl:with-param name="name" select="$attrNameXpath"/>
                        <xsl:with-param name="detail" select="$attrDetailXpath"/>
                    </xsl:call-template>
                </xsl:for-each>              
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:include href="attribute.xsl"/>

</xsl:stylesheet>
