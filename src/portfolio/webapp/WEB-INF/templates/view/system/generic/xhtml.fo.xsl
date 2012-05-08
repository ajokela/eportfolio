<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="p">
		<fo:block></fo:block>
		<xsl:apply-templates select="*|text()" />
		<fo:block></fo:block>
	</xsl:template>

	<xsl:template match="em">
		<fo:inline font-weight="bold">
			<xsl:apply-templates select="*|text()" />
		</fo:inline>
	</xsl:template>


	<xsl:template match="strong">
		<fo:inline font-weight="bold">
			<xsl:apply-templates select="*|text()" />
		</fo:inline>
	</xsl:template>




</xsl:stylesheet>
