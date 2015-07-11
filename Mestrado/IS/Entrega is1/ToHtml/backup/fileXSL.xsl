<?xml version="1.0" encoding="UTF-8"?>
<html xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0">
<head><title> News </title></head> <body> 
	<h1>News list</h1>
	<h2>World</h2>
	<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-world']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 


<h2>U.S</h2>
<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-us']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 
<h2>AFRICA</h2>
<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-africa']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 

<h2>ASIA</h2>
<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-asia']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 
<h2>EUROPE</h2>
<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-europe']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 
<h2>LATIN AMERICA</h2>
<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-latin-america']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 
<h2>MIDDLE EAST</h2>
<table border="1"> <tr>
		<td><b> Title </b></td> 
		<td><b> url </b></td> 
		<td><b> highlits </b></td>
		<td><b> day </b></td>
		<td><b> month </b></td>
		<td><b> year </b></td>
		<td><b> author </b></td>
		<td><b> text </b></td>
		<td><b> photos </b></td>
		<td><b> video </b></td>
	</tr>
	<xsl:for-each select="//regions/region[@area='nav-middle-east']/news"> <tr>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="url"/></td> 
		<td><xsl:value-of select="highlits"/> </td>
		<td><xsl:value-of select="date/day"/> </td>
		<td><xsl:value-of select="date/month"/> </td>
		<td><xsl:value-of select="date/year"/></td> 
		<td><xsl:value-of select="author"/> </td>
		<td><xsl:value-of select="text"/> </td>
		<td><xsl:value-of select="photos"/> </td>
		<td><xsl:value-of select="video"/> </td>
	</tr> 
	</xsl:for-each>
	
</table> 
</body>
</html>
