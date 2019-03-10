<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="http://tiendat.io/schema/categories"
                xmlns="http://tiendat.io/schema/categories"
                version="1.0">
    
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:categories" xmlns="http://tiendat.io/schema/categories">
        <xsl:element name="categories">
            <xsl:variable name="doc" select="document(@link_guitar_badon)"/>                  
            <xsl:for-each select="$doc//h2[@class='c-header mt50']/a[@class='see-all']">                            
                <xsl:if test="(not(contains(@href,'bao-chi')) and not(contains(@href,'dai-truyen-hinh')) and not(contains(@href,'video')))">                    
                    <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                        <xsl:variable name="cateName" select="normalize-space(@title)"/>                
                        <xsl:attribute name="categoryName">
                            <xsl:value-of select="$cateName"/>
                        </xsl:attribute>
                        <xsl:attribute name="link">
                            <xsl:value-of select="@href"/>
                        </xsl:attribute>
                        <xsl:variable name="detailCategory" select="document(@href)"/>
                        <xsl:call-template name="guitarList">
                            <xsl:with-param name="typeName"  select="$cateName" />
                            <xsl:with-param name="aElement" select="$detailCategory//div[@class='grid-row c-list']//div[@class='product list']/a"/>
                        </xsl:call-template>
                    </xsl:element>
                </xsl:if>                
            </xsl:for-each>
        </xsl:element>        
    </xsl:template>
    
    <xsl:template name="guitarList">
        <xsl:param name="aElement"/>  
        <xsl:param name="typeName"/>              
        <xsl:for-each select="document($aElement/@href)">               
            <xsl:variable name="detailInfo" select=".//div[@class='detail-info']"/>
            <xsl:variable name="imgUrl" select=".//div[@class='detail-content']//img[1]/@src"/>                                    
            <xsl:variable name="tempoPrice" select="$detailInfo//span[@class='price-sell']"/>
            <xsl:variable name="removeCommaPrice" select="translate($tempoPrice,',','')"/>            
            <xsl:variable name="removePostFix" select="substring-before($removeCommaPrice,'đ')"/>            
            <xsl:call-template name="guitarTemplate">                    
                <xsl:with-param name="name" select="$detailInfo/h1[@class='name']"/>
                <xsl:with-param name="category" select="$typeName"/>
                <xsl:with-param name="price" select="$removePostFix"/>
                <xsl:with-param name="imageUrl" select="$imgUrl"/>
                <xsl:with-param name="listFeature" select="$detailInfo/ul[@class='list-feature']"/>
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
                <xsl:for-each select="$listFeature/div">                    
                    <xsl:call-template name="attribute">
                        <xsl:with-param name="name" select="./b/font"/>
                        <xsl:with-param name="detail" select="text()"/>
                    </xsl:call-template>
                </xsl:for-each>              
                <xsl:for-each select="$listFeature//li">                    
                    <xsl:call-template name="attribute">
                        <xsl:with-param name="name" select="/span/text()"/>
                        <xsl:with-param name="detail" select="text()"/>
                    </xsl:call-template>
                </xsl:for-each>              
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:include href="attribute.xsl"/>
</xsl:stylesheet>