<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="ResidencyPS">
		<xsl:param name="entryName">
			Residency of Record
		</xsl:param>
		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>

							<!-- xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								<xsl:value-of select="residency/value"/><fo:block></fo:block> <xsl:value-of 
								select="residencyDt/value"/><fo:block></fo:block> <xsl:value-of select="effectiveTerm/value"/><br/ -->
							<xsl:value-of select="residencyDesc/value" />
							<!-- br/> <xsl:value-of select="termDesc/value"/><br/ -->


						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell3">
						<fo:block>
							<xsl:choose>
								<xsl:when test="elementMaterials/material">
									<xsl:apply-templates select="elementMaterials" />
								</xsl:when>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>

	</xsl:template>
</xsl:stylesheet>
