<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="SelfDirSearch">
		<xsl:param name="entryName">
			Self-Directed Search
		</xsl:param>


		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<fo:inline font-weight="bold">
								<xsl:value-of select="$entryName" />
								:
							</fo:inline>
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Inventory: </fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="dateTaken/value">
								<fo:inline font-weight="bold">Date Taken:</fo:inline>
								<xsl:value-of select="dateTaken/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="realistic/value">
								<fo:inline font-weight="bold">RIASEC type scores:</fo:inline>
								<fo:block></fo:block>
								Realistic:
								<xsl:value-of select="realistic/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="investigative/value">
								Investigative:
								<xsl:value-of select="investigative/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="artistic/value">
								Artistic:
								<xsl:value-of select="artistic/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="social/value">
								Social:
								<xsl:value-of select="social/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="enterprising/value">
								Enterprising:
								<xsl:value-of select="enterprising/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="conventional/value">
								Conventional:
								<xsl:value-of select="conventional/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="summaryCode/value">
								<fo:inline font-weight="bold">Summary Code:</fo:inline>
								<xsl:value-of select="summaryCode/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occuInfo/value">
								<fo:inline font-weight="bold">Occupational information:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="occuInfo/value" />
								<fo:block></fo:block>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="reaction/value">
								<fo:inline font-weight="bold">Interpretation / Reaction:</fo:inline>
								<xsl:apply-templates select="reaction/value" />
								<fo:block></fo:block>
							</xsl:if>

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
