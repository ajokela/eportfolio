<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="SelfDirSearch">
		<xsl:param name="entryName">
			Self-Directed Search
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<strong>
						<xsl:value-of select="$entryName" />
						:
					</strong>
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<xsl:if test="dateTaken/value">
						<strong>Date Taken: </strong>
						<xsl:value-of select="dateTaken/value" />
						<br />
					</xsl:if>
					<xsl:if test="realistic/value">
						<strong>RIASEC type scores:</strong>
						<br />
						Realistic:
						<xsl:value-of select="realistic/value" />
						<br />
					</xsl:if>
					<xsl:if test="investigative/value">
						Investigative:
						<xsl:value-of select="investigative/value" />
						<br />
					</xsl:if>
					<xsl:if test="artistic/value">
						Artistic:
						<xsl:value-of select="artistic/value" />
						<br />
					</xsl:if>
					<xsl:if test="social/value">
						Social:
						<xsl:value-of select="social/value" />
						<br />
					</xsl:if>
					<xsl:if test="enterprising/value">
						Enterprising:
						<xsl:value-of select="enterprising/value" />
						<br />
					</xsl:if>
					<xsl:if test="conventional/value">
						Conventional:
						<xsl:value-of select="conventional/value" />
						<br />
					</xsl:if>
					<xsl:if test="summaryCode/value">
						<strong>Summary Code: </strong>
						<xsl:value-of select="summaryCode/value" />
						<br />
					</xsl:if>
					<xsl:if test="occuInfo/value">
						<strong>Occupational information: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="occuInfo/value" />
						<br />
						<br />
					</xsl:if>
					<xsl:if test="reaction/value">
						<strong>Interpretation / Reaction: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="reaction/value" />
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
