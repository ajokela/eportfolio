<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="AddressPS">
		<xsl:param name="entryName">
			Address of Record
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
							<xsl:choose>
								<xsl:when
									test="((address1/value/text()) != ' ')or((address2/value/text()) != ' ')or((address3/value/text()) != ' ')or((address4/value/text()) != ' ')or((city/value/text()) != ' ')or((state/value/text()) != ' ')or((postal/value/text()) != ' ')or((country/value/text()) != ' ')">
									<xsl:if test="(address1/value/text()) != ' '">
										<xsl:value-of select="address1/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(address2/value/text()) != ' '">
										<xsl:value-of select="address2/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(address3/value/text()) != ' '">
										<xsl:value-of select="address3/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(address4/value/text()) != ' '">
										<xsl:value-of select="address4/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(city/value/text()) != ' '">
										<xsl:value-of select="city/value" />
									</xsl:if>
									<xsl:if test="(state/value/text()) != ' '">
										,
										<xsl:value-of select="state/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(postal/value/text()) != ' '">
										<xsl:value-of select="postal/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(country/value/text()) != ' '">
										<xsl:value-of select="country/value" />
										<fo:block></fo:block>
									</xsl:if>
								</xsl:when>
							</xsl:choose>
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
