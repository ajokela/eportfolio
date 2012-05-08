<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="GradPlan">
		<xsl:param name="entryName">
			Grad Plan
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
				</td>
				<td class="elemPresentationCell2">
					<a>
						<xsl:attribute name="href">javascript:openReportWindow('graduationPlanner.jsp?entryId='+encodeURIComponent('<xsl:value-of
							select="encodedEntryId/value" />'));</xsl:attribute>
						<xsl:value-of select="title/value" />
					</a>
				</td>
				<td class="elemPresentationCell3">
				</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>