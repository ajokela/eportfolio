<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="MyersBriggs">
		<xsl:param name="entryName">
			Personality Inventory
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">

					<xsl:choose>
						<xsl:when
							test="((testDate/value/text()) != ' ') or ((persType/value/text()) != ' ') or ((interpretation/value/text()) != ' ')">
							<xsl:if test="testDate/value">
								<strong>Date Taken: </strong>
								<xsl:value-of select="testDate/value" />
								<br />
							</xsl:if>
							<xsl:if test="persType/value">
								<strong>Personality Type: </strong>
								<xsl:value-of select="persType/value" />
								<br />
							</xsl:if>
							<xsl:if test="interpretation/value">
								<strong>Interpretation: </strong>
								<br />
								<xsl:value-of disable-output-escaping="yes"
									select="interpretation/value" />
								<br />
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[None Entered]</font>
						</xsl:otherwise>
					</xsl:choose>

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
