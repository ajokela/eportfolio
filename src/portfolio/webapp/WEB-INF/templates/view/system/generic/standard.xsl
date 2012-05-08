<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="html" />
	<xsl:template match="elementStandards">
		<xsl:if test="count(standard) > 0">
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
				<tr valign="top">
					<td class="elemPresentationCell1"></td>
					<td class="elemPresentationCell2">
						<!-- Show standards below descriptive text -->
						<strong>Public Tags:</strong>
						<br />
						<xsl:for-each select="standard">
							<xsl:if test="position() > 1">
								<br />
							</xsl:if>
							<text>- </text>
							<xsl:value-of select="standardText/value" />
						</xsl:for-each>
					</td>
					<td class="elemPresentationCell3"></td>
				</tr>
			</table>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>