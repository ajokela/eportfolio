<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="DegRequirement">
		<xsl:param name="entryName">
			Degree Requirements
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
					<fo:table-cell number-columns-spanned="2">
						<fo:block xsl:use-attribute-sets="td.elemPresentationCell2">
							<xsl:if test="bulletinYear/value">
								<fo:inline font-weight="bold">Bulletin Year:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="bulletinYear/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="requirements/value">
								<fo:inline font-weight="bold">Requirements:</fo:inline>
								<fo:block></fo:block>
								<fo:block white-space-collapse="false"
									linefeed-treatment="preserve">
									<xsl:apply-templates select="requirements/value" />
								</fo:block>
								<fo:block></fo:block>
							</xsl:if>
						</fo:block>
						<fo:block></fo:block>
						<fo:block xsl:use-attribute-sets="td.elemPresentationCell3">
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
