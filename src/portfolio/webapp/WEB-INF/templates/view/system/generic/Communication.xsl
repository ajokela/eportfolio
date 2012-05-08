<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="Communication">
		<xsl:param name="entryName">
			Communication Skills
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
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="expository/value">
						<strong>Expository Writing:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="expository/value" />
						<br />
					</xsl:if>
					<xsl:if test="creative/value">
						<strong>Creative Writing</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="creative/value" />
						<br />
					</xsl:if>
					<xsl:if test="discipline/value">
						<strong>Writing Within Discipline</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="discipline/value" />
						<br />
					</xsl:if>
					<xsl:if test="oneOnOne/value">
						<strong>One on One Interaction:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="oneOnOne/value" />
						<br />
					</xsl:if>
					<xsl:if test="smallGroup/value">
						<strong>Small Group Communication:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="smallGroup/value" />
						<br />
					</xsl:if>
					<xsl:if test="facilitation/value">
						<strong>Small Group Facilitation</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="facilitation/value" />
						<br />
					</xsl:if>
					<xsl:if test="publicSpeaking/value">
						<strong>Public Speaking:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="publicSpeaking/value" />
						<br />
					</xsl:if>
					<xsl:if test="listening/value">
						<strong>Listening:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="listening/value" />
						<br />
					</xsl:if>
					<xsl:if test="conflictRes/value">
						<strong>Conflict Resolution:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="conflictRes/value" />
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
