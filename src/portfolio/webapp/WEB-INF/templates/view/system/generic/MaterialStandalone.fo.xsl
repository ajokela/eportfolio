<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="UploadedMaterial">
		<xsl:param name="entryName">
			Uploaded Materials
		</xsl:param>

		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:text></xsl:text>
							<!--<xsl:value-of select="entryName/value"/> -->
							<xsl:value-of select="sampleName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<fo:inline font-weight="bold">Name:</fo:inline>
							<xsl:value-of select="sampleName/value" />
							<fo:block></fo:block>
							<xsl:choose>
								<xsl:when test="type/value = 'file'">
									<fo:inline font-weight="bold">File:</fo:inline>
									<fo:inline text-decoration="underline">
										<fo:basic-link>
											<xsl:attribute name="external-destination">url(attachments/<xsl:value-of
												select="name/value" />)
                                            </xsl:attribute>
											<xsl:value-of select="name/value" />
										</fo:basic-link>
									</fo:inline>
									<fo:block></fo:block>
									<fo:inline font-weight="bold">Size:</fo:inline>
									<xsl:value-of select="sampleSizeMB/value" />
									MB
								</xsl:when>
								<xsl:otherwise>
									<fo:inline font-weight="bold">URL:</fo:inline>
									<fo:inline text-decoration="underline">
										<fo:basic-link>
											<xsl:attribute name="external-destination">url(<xsl:value-of
												select="name/value" />)
                                            </xsl:attribute>
											<xsl:value-of select="name/value" />
										</fo:basic-link>
									</fo:inline>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell3">
						<fo:block></fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>
