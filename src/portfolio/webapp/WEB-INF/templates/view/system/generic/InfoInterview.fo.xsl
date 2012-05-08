<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="InfoInterview">
		<xsl:param name="entryName">
			Informational Interview
		</xsl:param>

		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:text></xsl:text>
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Person Interviewed:</fo:inline><xsl:text> </xsl:text><xsl:value-of select="entryName/value"/> 
								<fo:block></fo:block> </xsl:if -->
							<xsl:if test="intDate/value">
								<fo:inline font-weight="bold">Date of Interview:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="intDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation/value">
								<fo:inline font-weight="bold">Occupation:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="occupation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="organization/value">
								<fo:inline font-weight="bold">Institution/Organization:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="organization/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="infoOccupation/value">
								<fo:inline font-weight="bold">Information Obtained:</fo:inline>
								<fo:block></fo:block>
								<xsl:value-of select="infoOccupation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="addlNotes/value">
								<fo:inline font-weight="bold">Additional Information:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="addlNotes/value" />
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
