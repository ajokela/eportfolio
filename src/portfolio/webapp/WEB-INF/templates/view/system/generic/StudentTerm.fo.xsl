<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="StudentTerm">
		<xsl:param name="entryName">
			Registration Record
		</xsl:param>


		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>


					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:choose>
								<xsl:when test="position() > 1">
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="$entryName" />
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>


					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<fo:basic-link>
								<xsl:attribute name="external-destination">url(<xsl:value-of
									select="$entryName" />/<xsl:value-of select="entryName/value" />.pdf)</xsl:attribute>
								<fo:inline text-decoration="underline">
									<xsl:value-of select="entryName/value" />
								</fo:inline>
							</fo:basic-link>
							<fo:block></fo:block>
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
