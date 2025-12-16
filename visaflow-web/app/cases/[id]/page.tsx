"use client";

import api from "@/lib/api";
import { useQuery } from "@tanstack/react-query";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Loader2 } from "lucide-react";
import { Badge } from "@/components/ui/badge";

export default function CaseOverviewPage({ params }: { params: { id: string } }) {
    const { data: caseData, isLoading } = useQuery({
        queryKey: ["case", params.id],
        queryFn: async () => {
            const res = await api.get(`/cases/${params.id}`);
            return res.data;
        }
    });

    if (isLoading) return <Loader2 className="animate-spin" />;

    return (
        <div className="space-y-6">
            <div>
                <h3 className="text-lg font-medium">Case Overview</h3>
                <p className="text-sm text-muted-foreground">Detailed status of your application.</p>
            </div>

            <div className="grid gap-4 md:grid-cols-2">
                <Card>
                    <CardHeader>
                        <CardTitle className="text-sm font-medium">Case Information</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="text-2xl font-bold">{caseData?.petitionerName} & {caseData?.beneficiaryName}</div>
                        <div className="mt-2">
                            <Badge variant="outline">{caseData?.status}</Badge>
                        </div>
                    </CardContent>
                </Card>

                <Card>
                    <CardHeader>
                        <CardTitle className="text-sm font-medium">Progress</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="text-2xl font-bold">In Progress</div>
                        <p className="text-xs text-muted-foreground mt-1">
                            Complete the Questionnaire and upload documents to proceed.
                        </p>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
