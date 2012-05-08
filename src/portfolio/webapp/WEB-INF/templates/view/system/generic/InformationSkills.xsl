<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="InformationSkills">
		<xsl:param name="entryName">
			Information Skills
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
					<!-- xsl:if test="entryName/value"> <strong>Name of Skills:</strong><xsl:text> 
						</xsl:text> <xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="recognizeNeed/value">
						<strong>Recognize and Articulate the Need for Information:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="recognizeNeed/value" />
						<br />
					</xsl:if>
					<xsl:if test="accessInfo/value">
						<strong>Evaluate Information:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="accessInfo/value" />
						<br />
					</xsl:if>
					<xsl:if test="evaluateInfo/value">
						<strong>Access Needed Information:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="evaluateInfo/value" />
						<br />
					</xsl:if>
					<xsl:if test="useEffectively/value">
						<strong>Use Information Effectively to Accomplish a Specific
							Purpose:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="useEffectively/value" />
						<br />
					</xsl:if>
					<xsl:if test="useEthically/value">
						<strong>Use Information Ethically and Legally:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="useEthically/value" />
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
