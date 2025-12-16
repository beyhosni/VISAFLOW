"use client";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import api from "@/lib/api";
import { useMutation, useQuery } from "@tanstack/react-query";
import { Loader2, AlertTriangle, XCircle, CheckCircle2 } from "lucide-react";
import { useState } from "react";

export default function ValidationPage({ params }: { params: { id: string } }) {
    const [results, setResults] = useState<any[] | null>(null);

    const { data: answers } = useQuery({
        queryKey: ["answers", params.id],
        queryFn: async () => (await api.get(`/cases/${params.id}/questionnaire/answers`)).data
    });
    const { data: documents } = useQuery({
        queryKey: ["documents", params.id],
        queryFn: async () => (await api.get(`/cases/${params.id}/documents`)).data
    });

    const mutation = useMutation({
        mutationFn: async () => {
            // For Manual Testing on Frontend-only (if backend rules not fully connected via simple endpoint, we simulate logic or use the rules endpoint)
            // The backend Rules Controller expects { answers: {}, uploadedDocumentTypes: [] }
            const docTypes = documents?.map((d: any) => d.documentType) || [];

            const payload = {
                answers: answers || {},
                uploadedDocumentTypes: docTypes
            };

            return api.post(`/rules/validate`, payload);
        },
        onSuccess: (res) => {
            setResults(res.data);
        }
    });

    return (
        <div className="space-y-6">
            <div>
                <h3 className="text-lg font-medium">Review & Validation</h3>
                <p className="text-sm text-muted-foreground">Check your application for errors before generating the package.</p>
            </div>

            <Card>
                <CardHeader>
                    <CardTitle className="text-sm font-medium">Validation Status</CardTitle>
                </CardHeader>
                <CardContent>
                    {!results ? (
                        <div className="text-center py-6">
                            <p className="text-sm text-muted-foreground mb-4">Run validation to check for issues.</p>
                            <Button onClick={() => mutation.mutate()} disabled={mutation.isPending}>
                                {mutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                                Run Validation
                            </Button>
                        </div>
                    ) : results.length === 0 ? (
                        <div className="flex flex-col items-center justify-center text-green-600 py-6">
                            <CheckCircle2 className="h-12 w-12 mb-2" />
                            <h4 className="font-semibold">All Checks Passed!</h4>
                            <p className="text-sm text-muted-foreground">You can now generate your submission package.</p>
                        </div>
                    ) : (
                        <div className="space-y-3">
                            {results.map((res: any, idx: number) => (
                                <Alert key={idx} variant={res.severity === "ERROR" ? "destructive" : "default"}>
                                    {res.severity === "ERROR" ? <XCircle className="h-4 w-4" /> : <AlertTriangle className="h-4 w-4" />}
                                    <AlertTitle>{res.severity}</AlertTitle>
                                    <AlertDescription>
                                        {res.message} (Field: {res.fieldOrDocumentId})
                                    </AlertDescription>
                                </Alert>
                            ))}
                            <Button onClick={() => mutation.mutate()} variant="outline" className="mt-4">
                                Re-run Validation
                            </Button>
                        </div>
                    )}
                </CardContent>
            </Card>
        </div>
    );
}
