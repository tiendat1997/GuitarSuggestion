<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:ns2="http://tiendat.io/schema/attribute" 
                xmlns:ns3="http://tiendat.io/schema/guitar"
                version="1.0">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/">
        <html>            
            <body>                
                <xsl:apply-templates />
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="recommend-guitar">
        <div class="table">
            <div class="table-header">
                <div class="header__item">
                    <div class="header-row">
                        <div class="header__item"></div>
                    </div>
                </div>
                <div class="header__item">                    
                    <div class="header-row">                        
                        <div class="header__item"><p>Tên</p></div>
                        <div class="header__item"><p>Loại</p></div>
                        <div class="header__item"><p>Giá</p></div>
                    </div>
                </div>                    
            </div>            
            <div class="table-content">
                <xsl:for-each select="guitar">
                    <div class="table-row">
                        <div class="table-data">
                            <img>
                                <xsl:attribute name="src">                                    
                                    <xsl:value-of select="ns3:imageUrl"/>
                                </xsl:attribute>
                            </img>                                 
                        </div>
                        <div class="table-data">
                            <div class="row">                                
                                <div class="table-data" style="width:20%">
                                    <xsl:value-of select="ns3:name"/>                            
                                </div>
                                <div class="table-data" style="width:15%">
                                    <xsl:value-of select="ns3:category"/>                            
                                </div>
                                <div class="table-data" style="width:15%"> 
                                    <xsl:value-of select="ns3:price"/>
                                    <span> đ</span>                  
                                </div>
                            </div>
                            <hr/>
                            <div class="row">
                                <div class="table-data" style="width:40%">
                                    <ul>
                                        <xsl:variable name="attributes" select="ns3:attributes/ns2:attribute"/>
                                        <xsl:if test="$attributes">
                                            <xsl:for-each select="$attributes">
                                                <li class="row">
                                                    <div class="col-4">
                                                        <xsl:value-of select="ns2:name"/>
                                                    </div>
                                                    <div class="col-8">                                            
                                                        <xsl:value-of select="ns2:content"/>
                                                    </div>
                                                </li>
                                            </xsl:for-each>
                                        </xsl:if>
                                        <xsl:if test="not(boolean($attributes))">
                                            <li>
                                                Không chứa thuộc tính
                                            </li>
                                        </xsl:if>
                                    </ul>
                                </div>          
                            </div>
                        </div>                                                        
                    </div>
                </xsl:for-each>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
