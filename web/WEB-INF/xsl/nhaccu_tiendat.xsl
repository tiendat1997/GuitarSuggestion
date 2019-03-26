<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="http://tiendat.io/schema/categories"
                xmlns="http://tiendat.io/schema/categories"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:categories" xmlns="http://tiendat.io/schema/categories">
        <xsl:element name="categories">
            <!-- GUITAR -->
            <xsl:variable name="doc" select="document(@link_nhaccu_tiendat)"/>
            <xsl:variable name="guitarCategories" select="$doc//div[@id='left_column']//div[@class='box-content box-category']"/>
            <xsl:for-each select="$guitarCategories/div">                
                <xsl:variable name="cateLink" select="a"/>
                <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                    <xsl:variable name="cateName" select="$cateLink"/>
                    <xsl:variable name="cateHref" select="substring($cateLink/@href,1,string-length($cateLink/@href)-1)"/>
                    <xsl:attribute name="categoryName">
                        <xsl:value-of select="$cateName"/>
                    </xsl:attribute>
                    <xsl:attribute name="link">                            
                        <xsl:value-of select="$cateHref"/>
                    </xsl:attribute>    
                    <xsl:variable name="detailCategory" select="document($cateHref)"/>    
                    <xsl:call-template name="guitarList">
                        <xsl:with-param name="typeName"  select="$cateName" />
                        <xsl:with-param name="aElement" select="$detailCategory//div[@class='category']//div[@class='item']/a[1]"/>
                    </xsl:call-template>      
                </xsl:element>                          
            </xsl:for-each>
            <!-- END GUITAR -->
            <!-- UKULELE -->
            <xsl:variable name="ukuDoc" select="document(@link_nhaccu_tiendat_ukulele)"/>
            <xsl:variable name="ukuCategories" select="$ukuDoc//div[@id='left_column']//div[@class='box-content box-category']"/>
            <xsl:for-each select="$ukuCategories/div">                
                <xsl:variable name="cateLink" select="a"/>
                <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                    <xsl:variable name="cateName" select="$cateLink"/>
                    <xsl:variable name="cateHref" select="substring($cateLink/@href,1,string-length($cateLink/@href)-1)"/>
                    <xsl:attribute name="categoryName">
                        <xsl:value-of select="$cateName"/>
                    </xsl:attribute>
                    <xsl:attribute name="link">                            
                        <xsl:value-of select="$cateHref"/>
                    </xsl:attribute>    
                    <xsl:variable name="detailCategory" select="document($cateHref)"/>    
                    <xsl:call-template name="guitarList">
                        <xsl:with-param name="typeName"  select="$cateName" />
                        <xsl:with-param name="aElement" select="$detailCategory//div[@class='category']//div[@class='item']/a[1]"/>
                    </xsl:call-template>      
                </xsl:element>                          
            </xsl:for-each>
            <!-- END UKULELE -->            
        </xsl:element>        
    </xsl:template>
    
    <xsl:template name="guitarList">        
        <xsl:param name="aElement"/>          
        <xsl:param name="typeName"/>                
        <xsl:for-each select="document($aElement/@href)">            
            <xsl:variable name="tempoPrice" select=".//div[@class='product-price-group']/span[@class='price']"/>
            <xsl:variable name="removeCommaPrice" select="translate($tempoPrice,'.','')"/>        
            <xsl:variable name="removePostFixPrice" select="substring-before($removeCommaPrice,' VNĐ')" />                           
            <xsl:variable name="imageUrl" select=".//div[@id='product']//div[@class='product-image']//a/img/@src"/>    
            <xsl:call-template name="guitarTemplate">                    
                <xsl:with-param name="name" select=".//h1[@class='product-name']"/>
                <xsl:with-param name="category" select="$typeName"/>
                <xsl:with-param name="price" select="$removePostFixPrice"/>
                <xsl:with-param name="imageUrl" select="$imageUrl"/>
                <xsl:with-param name="listFeature" select=".//div[@class='product-desc']"/>
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
                <xsl:if test="boolean($listFeature/table)">
                    <xsl:for-each select="$listFeature/table/tbody/tr">                                        
                        <xsl:call-template name="attribute">
                            <xsl:with-param name="name" select="td[1]"/>
                            <xsl:with-param name="detail" select="td[2]"/>
                        </xsl:call-template>
                    </xsl:for-each>  
                </xsl:if>            
                <xsl:if test="not(boolean($listFeature/table))">
                    <xsl:for-each select="$listFeature/p">
                        <xsl:if test="contains(text(),':')">
                            <xsl:call-template name="attribute">
                                <xsl:with-param name="name" select="substring-before(text(),':')"/>
                                <xsl:with-param name="detail" select="substring-after(text(),':')"/>
                            </xsl:call-template>
                        </xsl:if>                                                                
                    </xsl:for-each>     
                </xsl:if>                              
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:include href="attribute.xsl"/>
    
</xsl:stylesheet>
