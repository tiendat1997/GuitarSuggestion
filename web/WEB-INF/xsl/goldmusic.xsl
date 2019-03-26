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
            <xsl:variable name="doc" select="document(@link_goldmusic)"/>
            <xsl:variable name="guitarCategories" select="$doc//ul[@class='box-category']/li"/>
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
                    <xsl:call-template name="guitarList">
                        <xsl:with-param name="typeName"  select="$cateName" />                                                
                        <xsl:with-param name="item" select="$detailCategory//div[@class='box_pro box_p_cat']/div[@class='item_pro_cat']/div[@class='product-thumb']/div[@class='caption']/div[@class='name']/a[not(contains(@href,' '))]"/>
                        <xsl:with-param name="nextPage" select="(($detailCategory//div[@class='panagi'])[1]/ul[@class='pagination']/li)[last()-1]"/>
                    </xsl:call-template>
                </xsl:element>
            </xsl:for-each>
            <!-- END GUITAR -->
            <!-- UKULELE --> 
            <xsl:variable name="ukuDoc" select="document(@link_goldmusic_ukulele)"/>
            <xsl:element name="category" xmlns="http://tiendat.io/schema/category">
                
            </xsl:element>
            <!-- END UKULELE -->
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="guitarList">                       
        <xsl:param name="typeName"/>                                
        <xsl:param name="item"/>   
        <xsl:param name="nextPage"/>
                
        <xsl:for-each select="document($item/@href)">
            <xsl:variable name="tempoPrice" select=".//div[@class='box_price']/span[@class='pri_new']"/>
            <xsl:variable name="removeCommaPrice" select="translate($tempoPrice,'.','')"/>        
            <xsl:variable name="removePostFixPrice" select="substring-before($removeCommaPrice,'đ')" />                           
            <xsl:variable name="imageUrl" select=".//div[@class='col-pro-image']//img/@src"/>    
            <xsl:variable name="info" select=".//div[@class='box-pro-info']"/>
            <xsl:if test="not(contains($info/h1[@class='title_pro'],'Dây Đàn'))">
                <xsl:call-template name="guitarTemplate">
                    <xsl:with-param name="name" select="$info/h1[@class='title_pro']"/>
                    <xsl:with-param name="category" select="$typeName"/>
                    <xsl:with-param name="price" select="$removePostFixPrice"/>
                    <xsl:with-param name="imageUrl" select="$imageUrl"/>
                    <xsl:with-param name="boxInfo" select="$info"/>
                    <xsl:with-param name="tabDesc" select=".//div[@id='tab-description']"/>
                </xsl:call-template>
            </xsl:if>            
        </xsl:for-each>
                 
        <!-- RECURSIVE PAGING -->
        <xsl:if test="$nextPage">
            <xsl:if test="string(number($nextPage/a)) = 'NaN'">                
                <xsl:variable name="nextDoc" select="document($nextPage/a/@href)"/>
                <xsl:call-template name="guitarList">
                    <xsl:with-param name="typeName" select="$typeName" />                        
                    <xsl:with-param name="item" select="$nextDoc//div[@class='box_pro box_p_cat']/div[@class='item_pro_cat']/div[@class='product-thumb']/div[@class='caption']/div[@class='name']/a[not(contains(@href,' '))]"/>
                    <xsl:with-param name="nextPage" select="(($nextDoc//div[@class='panagi'])[1]/ul[@class='pagination']/li)[last()-1]"/>
                </xsl:call-template>                
            </xsl:if> 
            <xsl:if test="not(string(number($nextPage/a)) = 'NaN')">            
            </xsl:if>  
        </xsl:if>      
        <xsl:if test="not($nextPage)">            
        </xsl:if>                 
    </xsl:template>        
    
    <xsl:template name="guitarTemplate">        
        <xsl:param name="name"/>
        <xsl:param name="category"/>
        <xsl:param name="price"/>
        <xsl:param name="imageUrl"/>
        <xsl:param name="boxInfo"/>
        <xsl:param name="tabDesc"/>
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
                <xsl:for-each select="$boxInfo//div[@class='pro_b_1']//ul/li">                    
                    <xsl:if test="contains(.,':')">
                        <xsl:call-template name="attribute">                        
                            <xsl:with-param name="name" select="normalize-space(substring-before(.,':'))"/>
                            <xsl:with-param name="detail" select="normalize-space(substring-after(.,':'))"/>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="not(contains(.,':'))">
                        <xsl:choose>
                            <xsl:when test="contains(.,'Body Wood')">
                                <xsl:call-template name="attribute">                        
                                    <xsl:with-param name="name">Body Wood</xsl:with-param>
                                    <xsl:with-param name="detail" select="normalize-space(substring-after(.,'Body Wood'))"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="contains(.,'Top Wood')">
                                <xsl:call-template name="attribute">                        
                                    <xsl:with-param name="name">Top Wood</xsl:with-param>
                                    <xsl:with-param name="detail" select="normalize-space(substring-after(.,'Top Wood'))"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="contains(.,'Shape')">
                                <xsl:call-template name="attribute">                        
                                    <xsl:with-param name="name">Shape</xsl:with-param>
                                    <xsl:with-param name="detail" select="normalize-space(substring-after(.,'Shape'))"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="contains(.,'Cutaway')">
                                <xsl:call-template name="attribute">                        
                                    <xsl:with-param name="name">Cutaway</xsl:with-param>
                                    <xsl:with-param name="detail" select="normalize-space(substring-after(.,'Cutaway'))"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="contains(.,'Electronics')">
                                <xsl:call-template name="attribute">                        
                                    <xsl:with-param name="name">Electronics</xsl:with-param>
                                    <xsl:with-param name="detail" select="normalize-space(substring-after(.,'Electronics'))"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="contains(.,'Specialty')">
                                <xsl:call-template name="attribute">                        
                                    <xsl:with-param name="name">Specialty</xsl:with-param>
                                    <xsl:with-param name="detail" select="normalize-space(substring-after(.,'Specialty'))"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>                            
                            </xsl:otherwise>
                        </xsl:choose>         
                    </xsl:if>                              
                </xsl:for-each>
                                
                <xsl:for-each select="$boxInfo//table//tr">                    
                    <xsl:if test="(./td[1]) and (./td[2])">
                        <xsl:call-template name="attribute">
                            <xsl:with-param name="name" select="normalize-space(./td[1])"/>
                            <xsl:with-param name="detail" select="normalize-space(./td[2])"/>
                        </xsl:call-template>
                    </xsl:if>                   
                </xsl:for-each>
                
                <!-- BREAK -->
                
                <xsl:for-each select="$tabDesc//ul/li">
                    <xsl:if test="contains(.,':')">
                        <xsl:call-template name="attribute">
                            <xsl:with-param name="name" select="normalize-space(substring-before(.,':'))"/>
                            <xsl:with-param name="detail" select="normalize-space(substring-after(.,':'))"/>
                        </xsl:call-template>
                    </xsl:if>                    
                </xsl:for-each>
                
                <xsl:for-each select="$tabDesc//div[@id='tab-tskt']/table//tr">                    
                    <xsl:call-template name="attribute">
                        <xsl:with-param name="name" select="normalize-space((./th)[1])"/>
                        <xsl:with-param name="detail" select="normalize-space((./td)[1])"/>
                    </xsl:call-template>
                </xsl:for-each>
                
                <xsl:for-each select="$tabDesc//p">                    
                    <xsl:if test="contains(.,':') and not(contains(.,'Liên hệ')) and not(string-length(.) > 25)">
                        <xsl:if test="string-length(substring-after(.,':')) > 0">
                            <xsl:call-template name="attribute">               
                                <xsl:with-param name="name" select="normalize-space(substring-before(.,':'))"/>
                                <xsl:with-param name="detail" select="normalize-space(substring-after(.,':'))"/>                        
                            </xsl:call-template>
                        </xsl:if>                        
                    </xsl:if>                    
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:include href="attribute.xsl"/>

</xsl:stylesheet>
