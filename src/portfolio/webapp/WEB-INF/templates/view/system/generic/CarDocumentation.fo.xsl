<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="CarDocumentation">
		<xsl:param name="entryName">
			Career Documentation
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
					<fo:table-cell number-columns-spanned="2">
						<fo:block white-space-collapse="false"
							linefeed-treatment="preserve" xsl:use-attribute-sets="td.elemPresentationCell2">
							<fo:inline font-weight="bold">Text:</fo:inline>
							<fo:block></fo:block>
							<xsl:apply-templates select="text/value" />
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
