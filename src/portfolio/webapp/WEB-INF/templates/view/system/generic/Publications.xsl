<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Publications">
		<xsl:param name="entryName">
			Publication
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<!-- <xsl:value-of select="elementMetaData/label/value"/> -->
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Title of publication: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="author/value">
						<strong>Author(s): </strong>
						<xsl:value-of disable-output-escaping="yes" select="author/value" />
						<br />
					</xsl:if>
					<xsl:if test="description/value">
						<strong>Description / Abstract: </strong>
						<br /><!-- xsl:value-of select="description/value"/ -->
						<xsl:variable name="myString" select="description/value" />
						<xsl:value-of disable-output-escaping="yes" select="$myString" />
						<br />
					</xsl:if>
					<xsl:if test="publDate/value">
						<strong>Year of publication: </strong>
						<xsl:value-of select="publDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="editor/value">
						<strong>Editors(s): </strong>
						<xsl:value-of disable-output-escaping="yes" select="editor/value" />
						<br />
					</xsl:if>
					<xsl:if test="collTitle/value">
						<strong>Title of collection: </strong>
						<xsl:value-of select="collTitle/value" />
						<br />
					</xsl:if>
					<xsl:if test="collVol/value">
						<strong>Volume and number of collection: </strong>
						<xsl:value-of select="collVol/value" />
						<br />
					</xsl:if>
					<xsl:if test="collPage/value">
						<strong>Page numbers: </strong>
						<xsl:value-of select="collPage/value" />
						<br />
					</xsl:if>
					<xsl:if test="pubLoc/value">
						<strong>Location of publisher: </strong>
						<xsl:value-of select="pubLoc/value" />
						<br />
					</xsl:if>
					<xsl:if test="pubName/value">
						<strong>Name of publisher: </strong>
						<xsl:value-of select="pubName/value" />
						<br />
					</xsl:if>
				</td>
				<td class="elemPresentationCell3">
					<xsl:choose>
						<xsl:when test="elementMaterials/material">
							<xsl:apply-templates select="elementMaterials" />
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[No Materials]</font>
						</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
