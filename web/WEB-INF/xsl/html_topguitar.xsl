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
                        <div class="header__item">
                            <p>Tên</p>
                        </div>
                        <div class="header__item">
                            <p>Loại</p>
                        </div>
                        <div class="header__item">
                            <p>Giá</p>
                        </div>
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
                                <div class="table-data" style="width:20%; padding: 5 0;">
                                    <b>
                                        <xsl:value-of select="ns3:name"/>                                         
                                    </b>   
                                    <hr/>
                                    <b style="color:#f5104d;">
                                        Điểm: <xsl:value-of select="ns3:weightedScore"/>                                         
                                    </b>    
                                </div>                                
                                <div class="table-data" style="width:15%">
                                    <b>
                                        <xsl:value-of select="ns3:category"/>
                                    </b>
                                </div>
                                <div class="table-data" style="width:15%"> 
                                    <b>
                                        <xsl:if test="contains(ns3:price,'.')">
                                            <xsl:value-of select="substring-before(ns3:price,'.')"/>
                                        </xsl:if>                                        
                                        <xsl:if test="not(contains(ns3:price,'.'))">
                                            <xsl:value-of select="ns3:price"/>
                                        </xsl:if>
                                        <span> đ</span>                  
                                    </b>
                                </div>
                            </div>                                
                            <!--                            <hr class="underline"/>-->
                            <div class="row">                                
                                <div class="table-data" style="width:40%">
                                    <div class="wrap-collabsible">
                                        <input class="toggle" type="checkbox" checked="checked">
                                            <xsl:attribute name="id">                                            
                                                <xsl:value-of select="concat('guitar-',ns3:id)"/>
                                            </xsl:attribute>
                                        </input>       
                                        <label class="lbl-toggle">
                                            <xsl:attribute name="for">
                                                <xsl:value-of select="concat('guitar-',ns3:id)"/>
                                            </xsl:attribute>
                                            Chi tiết
                                        </label>                                 
                                        <div class="collapsible-content">
                                            <div class="content-inner">
                                                <ul>
                                                    <xsl:variable name="attributes" select="ns3:attributes/ns2:attribute"/>
                                                    <xsl:if test="$attributes">
                                                        <xsl:for-each select="$attributes">
                                                            <li class="row">
                                                                <div class="col-4 attr-name">
                                                                    <xsl:value-of select="ns2:name"/>
                                                                </div>
                                                                <div class="col-8 attr-content">                                            
                                                                    <xsl:value-of select="ns2:content"/>
                                                                </div>
                                                            </li>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                    <xsl:if test="not(boolean($attributes))">
                                                        <li class="row">
                                                            <div class="col-12 attr-name">
                                                                Không chứa thuộc tính
                                                            </div>                                                           
                                                        </li>
                                                    </xsl:if>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>                                    
                                </div>          
                            </div>
                        </div>                                                        
                    </div>
                </xsl:for-each>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
