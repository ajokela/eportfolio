<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="GradThesis">
		<xsl:param name="entryName">
			Thesis
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
					<!-- xsl:if test="entryName/value"> <strong>Thesis Title:</strong><xsl:text> 
						</xsl:text> <xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="university/value">
						<strong>Univeristy of Completion:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="university/value" />
						<br />
					</xsl:if>
					<xsl:if test="defenseDate/value">
						<strong>Date of Defense:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="defenseDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="introduction/value">
						<strong>Introduction:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="introduction/value" />
						<br />
					</xsl:if>
					<xsl:if test="commentary/value">
						<strong>Abstract/Comments:</strong>
						<br /><!-- xsl:value-of select="commentary/value"/ -->
						<xsl:variable name="myString" select="commentary/value" />

						<xsl:value-of disable-output-escaping="yes" select="$myString" />
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
