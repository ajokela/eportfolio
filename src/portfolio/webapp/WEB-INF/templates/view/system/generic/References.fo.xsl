<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="References">
		<xsl:param name="entryName">
			Reference
		</xsl:param>


		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<!-- <xsl:value-of select="elementMetaData/label/value"/> -->
							<xsl:value-of select="$entryName" />
							:
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name: 
								</fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="position/value">
								<fo:inline font-weight="bold">Position:</fo:inline>
								<xsl:value-of select="position/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="organization/value">
								<fo:inline font-weight="bold">Institution / Organization:</fo:inline>
								<xsl:value-of select="organization/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="address1/value">
								<fo:inline font-weight="bold">Address:</fo:inline>
								<fo:block></fo:block>
								<xsl:value-of select="address1/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="address2/value">
								<xsl:value-of select="address2/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="city/value">
								<xsl:value-of select="city/value" />
								,
							</xsl:if>
							<xsl:if test="state/value">
								<xsl:value-of select="state/value" />
							</xsl:if>
							<xsl:if test="zip/value">
								<xsl:value-of select="zip/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="country/value">
								<xsl:value-of select="country/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="phone/value">
								<fo:inline font-weight="bold">Phone:</fo:inline>
								<xsl:value-of select="phone/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="email/value">
								<fo:inline font-weight="bold">E-mail Address:</fo:inline>
								<xsl:value-of select="email/value" />
								<fo:block></fo:block>
							</xsl:if>
							<fo:inline font-weight="bold">Contact Preferences:</fo:inline>
							<fo:block></fo:block>
							<xsl:if test="mailPref/value=1">
								Mail
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="phonePref/value=1">
								Phone
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="emailPref/value=1">
								E-mail
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
