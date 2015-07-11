<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template name="plaintext-paragraphs">
		<xsl:param name="string"/>	
		<xsl:choose>
			<xsl:when test="contains($string,'&#10;&#10;')">
				<p>				
					<xsl:call-template name="nl2br">
						<xsl:with-param name="string" select="substring-before($string,'&#10;&#10;')"/>
					</xsl:call-template>
				</p>				
				<xsl:call-template name="plaintext-paragraphs">
					<xsl:with-param name="string" select="substring-after($string,'&#10;&#10;')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<p>				
					<xsl:call-template name="nl2br">
						<xsl:with-param name="string" select="$string"/>
					</xsl:call-template>
				</p>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="nl2br">
		<xsl:param name="string"/>
		<xsl:value-of select="normalize-space(substring-before($string,'&#10;'))"/>
		<xsl:choose>
			<xsl:when test="contains($string,'&#10;')">
				<br />
				<xsl:call-template name="nl2br">
					<xsl:with-param name="string" select="substring-after($string,'&#10;')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

<xsl:template match="/">
<html>

<head><title> News </title>
<link rel="stylesheet" type="text/css" href="estilo.css"></link>
<script src="jquery-1.3.2.js"></script>
<script>
$(document).ready(function(){
	
	//Check to see if the window is top if not then display button
	$(window).scroll(function(){
		if ($(this).scrollTop() > 100) {
			$('.scrollToTop').fadeIn();
		} else {
			$('.scrollToTop').fadeOut();
		}
	});
	
	//Click event to scroll to top
	$('.scrollToTop').click(function(){
		$('html, body').animate({scrollTop : 0},800);
		return false;
	});
	
});
</script>
</head> 
<body> 
<a href="#" class="scrollToTop">Scroll To Top</a>
	<div class="title">
		<img id="cnnlogo" src="images/cnn-logo.jpg"></img> 
		<div class="news">News</div>
		<div class="zonas">
			<ul id="zonas_cima">
				<a href="#world"><li>World</li></a>
				<a href="#united"><li>United States</li></a>
				<a href="#africa"><li>Africa</li></a>
			</ul>
			<ul id="zonas_baixo">
				<a href="#asia"><li>Asia</li></a>
				<a href="#europe"><li>Europe</li></a>
				<a href="#latin"><li>Latin America</li></a>
				<a href="#middle"><li>Middle East</li></a>
			</ul>
		</div>
	</div>
	<div class="conteudo">
	
		<a name="world"></a> 
	<div class="titulo_zona">World</div>

	<xsl:for-each select="//regions/region[@area='nav-world']/news">
	<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<xsl:choose>
		<xsl:when test="highlits != ''">
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		</xsl:when>
		</xsl:choose>
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>

	

<a name="united"></a>
<div class="titulo_zona">United States</div>
 
	<xsl:for-each select="//regions/region[@area='nav-us']/news"> 
	<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<xsl:choose>
		<xsl:when test="highlits != ''">
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		</xsl:when>
		</xsl:choose>
	
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>
	
<a name="africa"></a>	 
<div class="titulo_zona">Africa</div>
 
	<xsl:for-each select="//regions/region[@area='nav-africa']/news"> 
		<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>

<a name="asia"></a> 
<div class="titulo_zona">Asia</div>

	<xsl:for-each select="//regions/region[@area='nav-asia']/news"> 
	<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<xsl:choose>
		<xsl:when test="highlits != ''">
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		</xsl:when>
		</xsl:choose>
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>

<a name="europe"></a> 	
<div class="titulo_zona">Europe</div>

	<xsl:for-each select="//regions/region[@area='nav-europe']/news"> 
	<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<xsl:choose>
		<xsl:when test="highlits != ''">
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		</xsl:when>
		</xsl:choose>
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>
 
 <a name="latin"></a> 
<div class="titulo_zona">Latin America</div>

	<xsl:for-each select="//regions/region[@area='nav-latin-america']/news"> 
		<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<xsl:choose>
		<xsl:when test="highlits != ''">
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		</xsl:when>
		</xsl:choose>
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>

	<a name="middle"></a>
<div class="titulo_zona">Middle East</div>
 
	<xsl:for-each select="//regions/region[@area='nav-middle-east']/news"> 
	<div class="noticia">
		<div class="titulo"><xsl:value-of select="title"/></div>
		<div class="data">
			<xsl:value-of select="date/day"/> &#160; <xsl:value-of select="date/month"/> &#160; <xsl:value-of select="date/year"/> &#160; - <xsl:value-of select="url"/>
			<br></br><xsl:value-of select="author"/> 
		</div>
		<div class="photo">
		<xsl:choose>
		<xsl:when test="photos != ''">
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="photos"/>
				</xsl:attribute>
			</img>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="video"/> 
		</xsl:otherwise>
		</xsl:choose>
		</div>
		<xsl:choose>
		<xsl:when test="highlits != ''">
		<div class="highlights">
			Highlights<p></p>
			<div class="high">
			<xsl:call-template name="plaintext-paragraphs">
				<xsl:with-param name="string" select="highlits"/>
			</xsl:call-template>
			</div>
		</div>
		</xsl:when>
		</xsl:choose>
		<div class="texto">
			
<xsl:call-template name="plaintext-paragraphs">
		<xsl:with-param name="string" select="text"/>
	</xsl:call-template>
		</div>
	</div>
	</xsl:for-each>
 
</div>


</body>
</html>

</xsl:template>

</xsl:stylesheet> 
