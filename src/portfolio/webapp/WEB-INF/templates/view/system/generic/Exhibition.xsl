<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="Exhibition">
		<xsl:param name="entryName">
			Exhibition
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:text> </xsl:text>
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name of Exhibition:</strong><xsl:text> 
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="type/value">
						<strong>Type of Exhibition: </strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="type/value" />
						<br />
					</xsl:if>
					<xsl:if test="fromDate/value">
						<strong>From:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="fromDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="toDate/value">
						<strong>To:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="toDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="location/value">
						<strong>Location:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="location/value" />
						<br />
					</xsl:if>
					<xsl:if test="juried/value">
						<strong>Juried:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="juried/value" />
						<br />
					</xsl:if>
					<xsl:if test="description/value">
						<strong>Description</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="description/value" />
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
