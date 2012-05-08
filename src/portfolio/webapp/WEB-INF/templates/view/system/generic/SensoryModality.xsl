<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="SensoryModality">
		<xsl:param name="entryName">
			Sensory Modality
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name of Inventory: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="dateTaken/value">
						<strong>Date Taken: </strong>
						<xsl:value-of select="dateTaken/value" />
						<br />
					</xsl:if>
					<xsl:if test="auditory/value">
						<strong>Auditory Results: </strong>
						<xsl:value-of select="auditory/value" />
						<br />
					</xsl:if>
					<xsl:if test="visual/value">
						<strong>Visual Results: </strong>
						<xsl:value-of select="visual/value" />
						<br />
					</xsl:if>
					<xsl:if test="kinesthetic/value">
						<strong>Kinesthetic Results: </strong>
						<xsl:value-of select="kinesthetic/value" />
						<br />
					</xsl:if>
					<xsl:if test="interpretation/value">
						<strong>Interpretation:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="interpretation/value" />
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
