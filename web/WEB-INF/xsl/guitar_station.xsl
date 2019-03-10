<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="http://tiendat.io/schema/categories"
                xmlns="http://tiendat.io/schema/categories"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:categories" xmlns="http://tiendat.io/schema/categories">
        <xsl:element name="categories">
            <xsl:variable name="doc" select="document(@link_gutiar_station)"/>
            <xsl:variable name="guitarCategories" select="$doc//ul[@class='product-categories']/li[1]"/>
            <xsl:for-each select="$guitarCategories/ul[@class='children']/li/a">
                <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                    <xsl:variable name="cateName" select="normalize-space(text())"/>
                    <xsl:attribute name="categoryName">
                        <xsl:value-of select="$cateName"/>
                    </xsl:attribute>
                    <xsl:attribute name="link">
                        <xsl:value-of select="@href"/>
                    </xsl:attribute>    
                    <xsl:variable name="detailCategory" select="document(@href)"/>    
                    <xsl:call-template name="guitarList">
                        <xsl:with-param name="typeName"  select="$cateName" />
                        <xsl:with-param name="aElement" select="$detailCategory//div[@class='box-image']//a"/>
                    </xsl:call-template>       
                </xsl:element>                
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="guitarList">        
        <xsl:param name="aElement"/>  
        <xsl:param name="typeName"/>                
        <xsl:for-each select="document($aElement/@href)">            
            <xsl:variable name="tempoPrice" select="(.//div[@class='info']/span[@class='guitar-price']//span[@class='woocommerce-Price-amount amount'])[last()]"/>
            <xsl:variable name="removeCommaPrice" select="translate($tempoPrice,',','')"/>        
            <xsl:variable name="removeNbspPrice" select="substring-before($removeCommaPrice,'&amp;#8363;')" />
            <xsl:variable name="imageUrl" select=".//section[@class='vertical-guitar-images']/div[contains(@class,'front-img')]/img/@src"/>
            <xsl:call-template name="guitarTemplate">                    
                <xsl:with-param name="name" select=".//div[@class='title']/h1"/>
                <xsl:with-param name="category" select="$typeName"/>
                <xsl:with-param name="price" select="$removeNbspPrice"/>
                <xsl:with-param name="imageUrl" select="$imageUrl"/>
                <xsl:with-param name="listFeature" select=".//section[@id='specs']//div[@class='spec']"/>
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
                <xsl:for-each select="$listFeature">                    
                    <xsl:call-template name="attribute">
                        <xsl:with-param name="name" select="./div[@class='spec-title']"/>
                        <xsl:with-param name="detail" select="./div[@class='spec-value']"/>
                    </xsl:call-template>
                </xsl:for-each>              
            </xsl:element>
        </xsl:element>
    </xsl:template>
    <xsl:include href="attribute.xsl"/>  
</xsl:stylesheet>
