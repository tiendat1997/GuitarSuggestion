<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="http://tiendat.io/schema/categories"
                xmlns="http://tiendat.io/schema/categories"
                version="1.0">
    
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:categories" xmlns="http://tiendat.io/schema/categories">
        <xsl:variable name="doc" select="document(@link_duy)"/>        
        <xsl:variable name="host" select="@host"/>
        <xsl:variable name="sidebar" select="$doc//aside[@id='sidebar-left']"/>        
        <xsl:element name="categories">            
            <xsl:for-each select="$sidebar//div[@class='box-content']//li[@class='' or @class='haschild']">                 
                <xsl:if test="boolean(@class='')">                    
                    <xsl:for-each select="./a">
                        <xsl:if test="boolean(@href)">
                            <xsl:variable name="typeName" select="normalize-space(text())"/>                                        
                            <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                                <xsl:attribute name="categoryName">
                                    <xsl:value-of select="$typeName"/>
                                </xsl:attribute>
                                <xsl:attribute name="link">
                                    <xsl:value-of select="@href"/>
                                </xsl:attribute>
                                <xsl:variable name="detailCategory" select="document(@href)"/>
                                <xsl:call-template name="guitarList">
                                    <xsl:with-param name="typeName" select="$typeName"/>
                                    <xsl:with-param name="aElement" select="$detailCategory//div[@class='product-block item-default']/div[@class='image']/a[@class='img']"/>
                                </xsl:call-template>                    
                            </xsl:element>
                        </xsl:if>              
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="boolean(@class='haschild')">                    
                    <xsl:for-each select="./ul/li//a">
                        <xsl:if test="boolean(@href)">                            
                            <xsl:variable name="typeName" select="substring-after(text(),'-')"/> 
                            <xsl:element name="category" xmlns="http://tiendat.io/schema/category">                                
                                <xsl:attribute name="categoryName">
                                    <xsl:value-of select="normalize-space($typeName)"/>
                                </xsl:attribute>
                                <xsl:attribute name="link">
                                    <xsl:value-of select="@href"/>
                                </xsl:attribute>
                                <xsl:variable name="detailCategory" select="document(@href)"/>
                                <xsl:call-template name="guitarList">
                                    <xsl:with-param name="typeName" select="$typeName"/>
                                    <xsl:with-param name="aElement" select="$detailCategory//div[@class='product-block item-default']/div[@class='image']/a[@class='img']"/>
                                </xsl:call-template>                    
                            </xsl:element>
                        </xsl:if>              
                    </xsl:for-each>
                </xsl:if>                
            </xsl:for-each>
        </xsl:element>      
    </xsl:template>        
    
    <xsl:template name="guitarList">
        <xsl:param name="aElement"/>  
        <xsl:param name="typeName"/>           
        <xsl:for-each select="document($aElement/@href)">            
            <xsl:variable name="name" select=".//h1[@class='title-product']"/>
            <xsl:variable name="description" select=".//div[@class='description']"/>
            <xsl:variable name="specification" select=".//div[@id='tab-specification']"/>
            <xsl:variable name="productView" select=".//div[@class='col-lg-6 col-md-6 col-sm-6 col-xs-12 product-view']"/>
            <xsl:variable name="price" select="$productView/div[@class='price']"/>
            <xsl:variable name="imageUrl" select=".//div[@class='product-info']//div[@class='image']/a/img/@src"/>     
            <xsl:if test="not(boolean($price))">
                <xsl:variable name="price" select="$productView/div[@class='price']/span[@class='price-new']"/>
            </xsl:if>
            <xsl:call-template name="guitarTemplate">
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="category" select="$typeName"/>
                <xsl:with-param name="imageUrl" select="$imageUrl"/>                
                <xsl:with-param name="price" select="$price"/>
                <xsl:with-param name="specification" select="$specification"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="guitarTemplate">
        <xsl:param name="imageUrl"/>
        <xsl:param name="name"/>     
        <xsl:param name="price"/>         
        <xsl:variable name="removeCommaPrice" select="translate($price,',','')"/>
        <xsl:variable name="normalizePrice" select="normalize-space($removeCommaPrice)"/>
        <xsl:param name="category"/>
        <xsl:param name="specification"/>
        <xsl:element name="guitar" xmlns="http://tiendat.io/schema/guitar">            
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>         
            <xsl:element name="category">
                <xsl:value-of select="$category"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:value-of select="substring-before($normalizePrice,'đ')"/>
            </xsl:element>
            <xsl:element name="imageUrl">
                <xsl:value-of select="$imageUrl"/>
            </xsl:element>
            <xsl:element name="attributes">
                <xsl:for-each select="$specification//tbody/tr">                    
                    <xsl:call-template name="attribute">
                        <xsl:with-param name="name" select="./td[1]"/>
                        <xsl:with-param name="detail" select="./td[2]"/>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:include href="attribute.xsl"/>
</xsl:stylesheet>
