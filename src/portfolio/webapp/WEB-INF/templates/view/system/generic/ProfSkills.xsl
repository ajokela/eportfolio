<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="ProfSkills">
		<xsl:param name="entryName">
			Professional Skills
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name of skill: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="description/value">
						<strong>Description: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="description/value" />
						<br />
					</xsl:if>
					<xsl:if test="experience/value">
						<strong>Experience: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="experience/value" />
						<br />
					</xsl:if>
					<xsl:if test="reflection/value">
						<strong>Reflection: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="reflection/value" />
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
