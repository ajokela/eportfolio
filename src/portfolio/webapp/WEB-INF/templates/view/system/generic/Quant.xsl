<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Quant">
		<xsl:param name="entryName">
			Quantitative Reasoning Skills
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name of skills: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="elemAlgebra/value">
						<strong>Elementary algebra: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="elemAlgebra/value" />
						<br />
					</xsl:if>
					<xsl:if test="colAlgebra/value">
						<strong>College algebra: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="colAlgebra/value" />
						<br />
					</xsl:if>
					<xsl:if test="geometry/value">
						<strong>Geometry: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="geometry/value" />
						<br />
					</xsl:if>
					<xsl:if test="trigonometry/value">
						<strong>Trigonometry :</strong>
						<xsl:value-of disable-output-escaping="yes"
							select="trigonometry/value" />
						<br />
					</xsl:if>
					<xsl:if test="calculus/value">
						<strong>Calculus: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="calculus/value" />
						<br />
					</xsl:if>
					<xsl:if test="higherMath/value">
						<strong>Higher mathematics: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="higherMath/value" />
						<br />
					</xsl:if>
					<xsl:if test="statistics/value">
						<strong>Statistics: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="statistics/value" />
						<br />
					</xsl:if>
					<xsl:if test="accounting/value">
						<strong>Accounting: </strong>
						<xsl:value-of disable-output-escaping="yes"
							select="accounting/value" />
						<br />
					</xsl:if>
					<xsl:if test="logic/value">
						<strong>Logic: </strong>
						<xsl:value-of disable-output-escaping="yes" select="logic/value" />
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
