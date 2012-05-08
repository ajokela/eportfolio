<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="DemographicPS">
		<xsl:param name="entryName">
			Demographic Information
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
							<xsl:if test="birthdate/value">
								<fo:inline font-weight="bold">Birth Date:</fo:inline>
								<xsl:value-of select="birthdate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="sex/value">
								<fo:inline font-weight="bold">Sex:</fo:inline>
								<xsl:value-of select="sex/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="citizenshipStatus/value">
								<fo:inline font-weight="bold">Citizenship Status:</fo:inline>
								<xsl:value-of select="citizenshipStatus/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="ethnicGroup/value">
								<fo:inline font-weight="bold">Ethnic Group:</fo:inline>
								<xsl:value-of select="ethnicGroup/value" />
								<fo:block></fo:block>
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
