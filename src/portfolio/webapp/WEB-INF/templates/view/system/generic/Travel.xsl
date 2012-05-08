<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Travel">
		<xsl:param name="entryName">
			Travel
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<!-- <xsl:value-of select="elementMetaData/label/value"/> -->
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Area of Travel: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="fromDate/value">
						<strong>From: </strong>
						<xsl:value-of select="fromDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="toDate/value">
						<strong>To: </strong>
						<xsl:value-of select="toDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="description/value">
						<strong>Description:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="description/value" />
						<br />
					</xsl:if>
					<xsl:if test="reflection/value">
						<strong>Reflection:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="reflection/value" />
						<br />
					</xsl:if>

					<!-- <xsl:if test="count(elementMaterials/material) > 0"> <br/> <h3> 
						<xsl:text> Materials: </xsl:text> <xsl:for-each select="elementMaterials/material"> 
						<xsl:if test="position() > 1"> <xsl:text> | </xsl:text> </xsl:if> <a target="ospiMaterial"> 
						<xsl:attribute name="href"><xsl:value-of select="showMaterialUrl/value"/></xsl:attribute> 
						<xsl:value-of select="sampleName/value"/> </a> <xsl:text> </xsl:text> </xsl:for-each> 
						</h3> </xsl:if> -->
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
