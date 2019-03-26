<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="http://tiendat.io/schema/categories"
                xmlns="http://tiendat.io/schema/categories"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:categories" xmlns="http://tiendat.io/schema/categories">       
        <xsl:element name="categories">
            <xsl:variable name="doc" select="document(@link_tongkhonhaccu)"/>
            <xsl:variable name="guitarCategories" select="($doc//div[@class='filter-bg']/ul)[2]/li"/>                                    
            <xsl:for-each select="$guitarCategories">
                <xsl:variable name="cateLink" select="a"/>
                <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                    <xsl:variable name="cateName" select="$cateLink"/>
                    <xsl:variable name="cateHref" select="$cateLink/@href"/>
                    <xsl:attribute name="categoryName">
                        <xsl:value-of select="$cateName"/>
                    </xsl:attribute>                    
                    <xsl:attribute name="link">                            
                        <xsl:value-of select="$cateHref"/>
                    </xsl:attribute>    
                    
                    <xsl:variable name="detailCategory" select="document($cateHref)"/>        
                    <test>
                        <xsl:value-of select="$cateHref"/>
                    </test>                                    
                    <xsl:call-template name="guitarList">
                        <xsl:with-param name="typeName"  select="$cateName" />                                                
                        <xsl:with-param name="item" select="$detailCategory//ul[@class='listproduct02 listproduct listproduct-col3']//div[@class='bglipro']/div[@class='c-product-item_info']/h4/a"/>                                               
                    </xsl:call-template>                    
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
    <xsl:template name="guitarList">
        <xsl:param name="typeName"/>                                
        <xsl:param name="item"/>    
                
        <xsl:for-each select="document($item[not(contains(@href,' ') or contains(@href,'ó'))]/@href)">            
            <xsl:variable name="tempoPrice" select=".//span[@class='curr-price all']"/>                           
            <xsl:variable name="imageUrl" select=".//div[@class='pdl-image zoom']//img/@src"/>                
            <xsl:call-template name="guitarTemplate">
                <xsl:with-param name="name" select=".//h1[@class='pdrl-name']"/>
                <xsl:with-param name="category" select="$typeName"/>
                <xsl:with-param name="price" select="translate($tempoPrice, translate($tempoPrice, '0123456789', ''), '')"/>
                <xsl:with-param name="imageUrl" select="concat('https://tongkhonhaccu.com',$imageUrl)"/>
                <xsl:with-param name="listFeature" select="(.//div[@id='content']/div)[1]"/>                    
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="guitarTemplate">        
        <xsl:param name="name"/>
        <xsl:param name="category"/>
        <xsl:param name="price"/>
        <xsl:param name="imageUrl"/>
        <xsl:param name="listFeature"/>
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
                <xsl:if test="boolean($listFeature)">
                    <xsl:for-each select="$listFeature/p">                                                               
                        <xsl:call-template name="attribute">
                            <xsl:with-param name="name" select="substring-before(.,':')"/>
                            <xsl:with-param name="detail" select="substring-after(.,':')"/>
                        </xsl:call-template>
                    </xsl:for-each>  
                </xsl:if>                                             
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:include href="attribute.xsl"/>
</xsl:stylesheet>
