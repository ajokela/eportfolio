<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="SensoryModality">
		<xsl:param name="entryName">
			Sensory Modality
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
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Inventory: </fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="dateTaken/value">
								<fo:inline font-weight="bold">Date Taken:</fo:inline>
								<xsl:value-of select="dateTaken/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="auditory/value">
								<fo:inline font-weight="bold">Auditory Results:</fo:inline>
								<xsl:value-of select="auditory/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="visual/value">
								<fo:inline font-weight="bold">Visual Results:</fo:inline>
								<xsl:value-of select="visual/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="kinesthetic/value">
								<fo:inline font-weight="bold">Kinesthetic Results:</fo:inline>
								<xsl:value-of select="kinesthetic/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interpretation/value">
								<fo:inline font-weight="bold">Interpretation:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="interpretation/value" />
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
