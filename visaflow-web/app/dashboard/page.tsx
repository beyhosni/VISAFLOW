"use client";

import { AppNavbar } from "@/components/app-navbar";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import api from "@/lib/api";
import { useQuery } from "@tanstack/react-query";
import { Loader2, Plus } from "lucide-react";
import Link from "next/link";

interface Case {
    id: string;
    petitionerName: string;
    beneficiaryName: string;
    status: string;
    stage: string;
    createdAt: string;
    updatedAt: string;
}

export default function DashboardPage() {
    const { data: cases, isLoading, error } = useQuery<Case[]>({
        queryKey: ["cases"],
        queryFn: async () => {
            const res = await api.get("/cases");
            return res.data;
        }
    });

    return (
        <div className="flex flex-col min-h-screen">
            <AppNavbar />
            <main className="flex-1 container py-8">
                <div className="flex items-center justify-between mb-8">
                    <div>
                        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
                        <p className="text-muted-foreground">Manage your visa applications.</p>
                    </div>
                    <Link href="/cases/new">
                        <Button>
                            <Plus className="mr-2 h-4 w-4" /> New Case
                        </Button>
                    </Link>
                </div>

                {isLoading ? (
                    <div className="flex justify-center py-20">
                        <Loader2 className="h-8 w-8 animate-spin" />
                    </div>
                ) : error ? (
                    <div className="p-4 border border-destructive/50 text-destructive rounded-md">
                        Error loading cases. Is backend running?
                    </div>
                ) : cases && cases.length === 0 ? (
                    <div className="flex flex-col items-center justify-center py-20 border-2 border-dashed rounded-lg">
                        <h3 className="text-xl font-semibold mb-2">No cases found</h3>
                        <p className="text-muted-foreground mb-6">Get started by creating your first visa application.</p>
                        <Link href="/cases/new">
                            <Button>Create Case</Button>
                        </Link>
                    </div>
                ) : (
                    <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                        {cases?.map((c) => (
                            <Card key={c.id}>
                                <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                                    <CardTitle className="text-sm font-medium">I-130 Petition</CardTitle>
                                    <span className="text-xs text-muted-foreground">{new Date(c.createdAt).toLocaleDateString()}</span>
                                </CardHeader>
                                <CardContent>
                                    <div className="text-2xl font-bold">{c.petitionerName} & {c.beneficiaryName}</div>
                                    <p className="text-xs text-muted-foreground mt-1">Status: {c.status}</p>
                                    <Link href={`/cases/${c.id}`}>
                                        <Button variant="secondary" className="w-full mt-4">Open Case</Button>
                                    </Link>
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                )}

            </main>
        </div>
    );
}
