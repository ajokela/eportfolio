<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="Service">
		<xsl:param name="entryName">
			Service
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
									test="((organization/value/text()) != ' ') or ((supervisor/value/text()) != ' ') or ((street1/value/text()) != ' ') or ((street2/value/text()) != ' ') or ((city/value/text()) != ' ') or ((state/value/text()) != ' ') or ((zip/value/text()) != ' ') or ((country/value/text()) != ' ') or ((phone/value/text()) != ' ') or ((email/value/text()) != ' ') or ((fax/value/text()) != ' ') or ((startDate/value/text()) != ' ') or ((endDate/value/text()) != ' ') or ((hours/value/text()) != ' ') or ((description/value/text()) != ' ') or ((comments/value/text()) != ' ')">
									<xsl:if test="organization/value">
										<fo:inline font-weight="bold">Organization:</fo:inline>
										<xsl:value-of select="organization/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="supervisor/value">
										<fo:inline font-weight="bold">Supervisor:</fo:inline>
										<xsl:value-of select="supervisor/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="street1/value">
										<fo:inline font-weight="bold">Address:</fo:inline>
										<xsl:value-of select="street1/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="street2/value">
										<fo:inline font-weight="bold">Address 2:</fo:inline>
										<xsl:value-of select="street2/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="city/value">
										<fo:inline font-weight="bold">City:</fo:inline>
										<xsl:value-of select="city/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="state/value">
										<fo:inline font-weight="bold">State:</fo:inline>
										<xsl:value-of select="state/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="zip/value">
										<fo:inline font-weight="bold">Zip:</fo:inline>
										<xsl:value-of select="zip/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="country/value">
										<fo:inline font-weight="bold">Country:</fo:inline>
										<xsl:value-of select="country/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="phone/value">
										<fo:inline font-weight="bold">Phone:</fo:inline>
										<xsl:value-of select="phone/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="email/value">
										<fo:inline font-weight="bold">Email:</fo:inline>
										<xsl:value-of select="email/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="fax/value">
										<fo:inline font-weight="bold">Fax:</fo:inline>
										<xsl:value-of select="fax/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="startDate/value">
										<fo:inline font-weight="bold">Start Date:</fo:inline>
										<xsl:value-of select="startDate/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="endDate/value">
										<fo:inline font-weight="bold">End Date:</fo:inline>
										<xsl:value-of select="endDate/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="hours/value">
										<fo:inline font-weight="bold">Hours:</fo:inline>
										<xsl:value-of select="hours/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="presentlyEmployed/value">
										(presently employed)
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="description/value">
										<fo:inline font-weight="bold">Description:</fo:inline>
										<fo:block></fo:block>
										<xsl:apply-templates select="description/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="comments/value">
										<fo:inline font-weight="bold">Comments:</fo:inline>
										<fo:block></fo:block>
										<xsl:apply-templates select="comments/value" />
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
