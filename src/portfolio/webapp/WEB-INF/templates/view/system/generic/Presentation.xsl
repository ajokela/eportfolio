<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Presentation">
		<xsl:param name="entryName">
			Presentation
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Title of presentation: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="description/value">
						<strong>Description: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="description/value" />
						<br />
					</xsl:if>
					<xsl:if test="presenters/value">
						<strong>Presenters: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="presenters/value" />
						<br />
					</xsl:if>
					<xsl:if test="eventName/value">
						<strong>Name of Event: </strong>
						<xsl:value-of select="eventName/value" />
						<br />
					</xsl:if>
					<xsl:if test="location/value">
						<strong>Location: </strong>
						<xsl:value-of select="location/value" />
						<br />
					</xsl:if>
					<xsl:if test="eventDate/value">
						<strong>Presentation Date: </strong>
						<xsl:value-of select="eventDate/value" />
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
